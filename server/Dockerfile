FROM eclipse-temurin:17-jdk-jammy AS build

WORKDIR /src
COPY . .
RUN ./gradlew :server:installDist --no-daemon

FROM eclipse-temurin:17-jre-jammy

RUN useradd --system --create-home --home-dir /var/lib/legado-server legado
COPY --from=build /src/server/build/install/legado-server /opt/legado-server

ENV LEGADO_SERVER_DATA=/var/lib/legado-server
EXPOSE 1122 1123
USER legado

ENTRYPOINT ["/opt/legado-server/bin/legado-server"]
CMD ["--host", "0.0.0.0", "--port", "1122"]
