# reference

只读的迁移证据。**本目录下任何内容都不在构建路径上。**

## `decompiled/`

用 CFR 0.152 对 `reader-pro-3.2.14.jar` 的应用类（`BOOT-INF/classes`，936 个 `.class`）反编译的产物，反编译时把制品自带的 `BOOT-INF/lib` 作为额外 classpath 传入。

这棵树的唯一用途是回答"参考字节码到底做了什么"，供重建 `src/` 下的可读源码时对照。**它无法编译，绝不能被搬进 `src/`**：

- 每个 `suspend` 函数都是协程状态机，CFR 会报 `Unable to fully structure code` 并输出基于 `switch` 的状态机帧；
- 有 2 个方法彻底反编译失败（见 `cfr-report.txt`）；
- Kotlin 默认参数、内联泛型和合成的 `$$forInline` 成员在 Java 中没有对应写法。

`cfr-report.txt` 是整轮反编译的逐方法报告。

## 溯源

| 字段 | 值 |
| --- | --- |
| 制品 | `reader-pro-3.2.14.jar` |
| 大小 | 72,913,887 字节 |
| SHA-256 | `b26fb4769d689d98ff26408ce79a275d719f360906c84acf52ff404e98030c8c` |
| 反编译器 | CFR 0.152 |
| 应用类数量 | 936（全部为 class major 52 / Java 8） |
| 内嵌依赖 | `BOOT-INF/lib` 下 85 个，见 `docs/dependencies.txt` |

原始制品**未**纳入版本库。请自备副本，并把本目录作为对照基线前先按上面的 SHA-256 校验。
