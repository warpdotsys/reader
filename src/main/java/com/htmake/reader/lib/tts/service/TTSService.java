/*
 * Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
 */

package com.htmake.reader.lib.tts.service;

import com.htmake.reader.lib.tts.constant.OutputFormat;
import com.htmake.reader.lib.tts.exceptions.TtsException;
import com.htmake.reader.lib.tts.model.SSML;
import com.htmake.reader.lib.tts.model.SpeechConfig;
import com.htmake.reader.lib.tts.util.Tools;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.Buffer;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TTSService {
    public static final Logger log = LoggerFactory.getLogger(TTSService.class);
    private OutputFormat outputFormat;
    private boolean usingAzureApi;
    private volatile boolean synthesising;
    private String currentText;
    private final Buffer audioBuffer = new Buffer();
    private OkHttpClient okHttpClient;
    private WebSocket ws;
    private CountDownLatch latch;
    protected WebSocketListener webSocketListener = new WebSocketListener(){

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            super.onClosed(webSocket, code, reason);
            log.debug("onClosed:" + reason);
            TTSService.this.ws = null;
            TTSService.this.synthesising = false;
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            super.onClosing(webSocket, code, reason);
            log.debug("onClosing:" + reason);
            TTSService.this.ws = null;
            TTSService.this.synthesising = false;
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response2) {
            super.onFailure(webSocket, t, response2);
            log.debug("onFailure" + t.getMessage(), t);
            TTSService.this.ws = null;
            TTSService.this.synthesising = false;
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            super.onMessage(webSocket, text);
            if (text.contains("turn.start")) {
                TTSService.this.audioBuffer.clear();
            } else if (text.contains("turn.end")) {
                TTSService.this.latch.countDown();
                TTSService.this.synthesising = false;
            }
        }

        @Override
        public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes2) {
            boolean audioContentType;
            super.onMessage(webSocket, bytes2);
            int audioIndex = bytes2.lastIndexOf("Path:audio\r\n".getBytes(StandardCharsets.UTF_8)) + "Path:audio\r\n".length();
            boolean bl = audioContentType = bytes2.lastIndexOf("Content-Type:audio".getBytes(StandardCharsets.UTF_8)) + "Content-Type:audio".length() != -1;
            if (audioIndex != -1 && audioContentType) {
                try {
                    TTSService.this.audioBuffer.write(bytes2.substring(audioIndex));
                }
                catch (Exception e) {
                    log.error("onMessage Error," + e.getMessage(), e);
                }
            }
        }
    };

    private TTSService(OutputFormat outputFormat, boolean usingAzureApi) {
        this.outputFormat = outputFormat;
        this.usingAzureApi = usingAzureApi;
    }

    public static TTSServiceBuilder builder() {
        return new TTSServiceBuilder();
    }

    public byte[] sendText(SSML ssml) {
        while (this.synthesising) {
            log.info("\u7a7a\u8f6c\u7b49\u5f85\u4e0a\u4e00\u4e2a\u8bed\u97f3\u5408\u6210");
            Tools.sleep(1);
        }
        this.latch = new CountDownLatch(1);
        this.synthesising = true;
        if (Objects.nonNull((Object)ssml.getStyle()) && !this.usingAzureApi) {
            ssml.setStyle(null);
        }
        if (Objects.nonNull((Object)ssml.getOutputFormat()) && !this.outputFormat.equals((Object)ssml.getOutputFormat())) {
            this.sendConfig(ssml.getOutputFormat());
        }
        log.info("ssml:{}", (Object)ssml);
        if (!this.getOrCreateWs().send(ssml.toString())) {
            throw TtsException.of("\u8bed\u97f3\u5408\u6210\u8bf7\u6c42\u53d1\u9001\u5931\u8d25...");
        }
        this.currentText = ssml.getSynthesisText();
        try {
            this.latch.await(30L, TimeUnit.SECONDS);
            return this.audioBuffer.readByteArray();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private synchronized WebSocket getOrCreateWs() {
        String origin;
        String url2;
        if (Objects.nonNull(this.ws)) {
            return this.ws;
        }
        if (this.usingAzureApi) {
            url2 = "wss://eastus.api.speech.microsoft.com/cognitiveservices/websocket/v1?Retry-After=200&TrafficType=AzureDemo&Authorization=bearer undefined&X-ConnectionId=" + Tools.getRandomId();
            origin = "https://azure.microsoft.com";
        } else {
            url2 = "wss://speech.platform.bing.com/consumer/speech/synthesize/readaloud/edge/v1?Retry-After=200&TrustedClientToken=6A5AA1D4EAFF4E9FB37E23D68491D6F4&ConnectionId=" + Tools.getRandomId();
            origin = "chrome-extension://jdiccldimpdaibmpdkjnbmckianbfold";
        }
        Request request = new Request.Builder().url(url2).addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36 Edg/111.0.1661.44").addHeader("Origin", origin).build();
        this.ws = this.getOkHttpClient().newWebSocket(request, this.webSocketListener);
        this.sendConfig(this.outputFormat);
        return this.ws;
    }

    private OkHttpClient getOkHttpClient() {
        if (this.okHttpClient == null) {
            this.okHttpClient = new OkHttpClient.Builder().pingInterval(20L, TimeUnit.SECONDS).build();
        }
        return this.okHttpClient;
    }

    private void sendConfig(OutputFormat outputFormat) {
        SpeechConfig speechConfig = SpeechConfig.of(outputFormat);
        log.info("audio config:{}", (Object)speechConfig);
        if (!this.getOrCreateWs().send(speechConfig.toString())) {
            throw TtsException.of("\u8bed\u97f3\u8f93\u51fa\u683c\u5f0f\u914d\u7f6e\u5931\u8d25...");
        }
        this.outputFormat = speechConfig.getOutputFormat();
    }

    public static class TTSServiceBuilder {
        private OutputFormat outputFormat;
        private boolean usingAzureApi;

        public TTSServiceBuilder usingOutputFormat(OutputFormat usingOutputFormat) {
            this.outputFormat = usingOutputFormat;
            return this;
        }

        public TTSServiceBuilder usingAzureApi(boolean usingAzureApi) {
            this.usingAzureApi = usingAzureApi;
            return this;
        }

        public TTSService build() {
            return new TTSService(this.outputFormat, this.usingAzureApi);
        }
    }
}

