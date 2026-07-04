# Big Dog Bark

一个客户端 Minecraft 模组：将弓和三叉戟的蓄力和发射音效改为大狗叫。

Changed the charge and firing sound effects of bows and tridents to bigdogbark.

支持：

- Fabric `1.20.1` / `1.21.1`
- Forge `1.20.1` / `1.21.1`
- NeoForge `1.21.1`

## 功能

- 蓄力时重复播放 `bigdogbark:bigdog`
- 第一次 `bigdog` 使用正常音调和速度
- 蓄力越久，`bigdog` 的 pitch 越高，Minecraft 会同时让播放速度听起来更快
- 相邻 `bigdog` 之间会重叠播放，正常 pitch 下约提前 `0.2s` 播放下一次，后续间隔随 pitch 提高继续缩短
- 松开蓄力后播放 `bigdogbark:bark`
- 不触发弩，仅处理弓和三叉戟

## 音频资源

Minecraft 资源包音频应使用 OGG Vorbis 格式。

Fabric 音频路径：

```text
common/src/main/resources/assets/bigdogbark/sounds/bigdog.ogg
common/src/main/resources/assets/bigdogbark/sounds/bark.ogg
```

Forge 音频路径：

```text
forge/common/src/main/resources/assets/bigdogbark/sounds/bigdog.ogg
forge/common/src/main/resources/assets/bigdogbark/sounds/bark.ogg
```

NeoForge 音频路径：

```text
neoforge/src/main/resources/assets/bigdogbark/sounds/bigdog.ogg
neoforge/src/main/resources/assets/bigdogbark/sounds/bark.ogg
```
## Fabric 构建

根目录是 Fabric 多模块工程：

```bash
./gradlew build
```

当前构建配置使用 Fabric Loom `1.15.5`，适配 Gradle `9.2.x`。如果用系统 Gradle，请确认版本至少是 `9.2.0`。

产物位置：

```text
versions/1.20.1/build/libs/
versions/1.21.1/build/libs/
```

放进游戏 `mods` 文件夹时使用不带 `-sources` 的 jar：

```text
versions/1.20.1/build/libs/bigdogbark-1.20.1-1.1.0.jar
versions/1.21.1/build/libs/bigdogbark-1.21.1-1.1.0.jar
```

如果当前机器还没有 Gradle wrapper，请先在有 Gradle 的环境中执行：

```bash
gradle wrapper --gradle-version 9.2.1
```

然后再使用 `./gradlew build`。

## Forge 构建

Forge 是独立 Gradle 工程，目录为 `forge/`：

```bash
cd forge
./gradlew build
```

当前 Forge 工程使用 ForgeGradle `6.0.54` 和 Gradle `8.8` wrapper。若 ForgeGradle 证书检查或外部仓库 TLS 在当前网络环境下失败，可以使用已经缓存好的本地工件离线构建：

```bash
cd forge
./gradlew --offline -Dnet.minecraftforge.gradle.check.certs=false build
```

Forge 产物位置：

```text
forge/versions/1.20.1/build/libs/
forge/versions/1.21.1/build/libs/
```

放进游戏 `mods` 文件夹时使用不带 `-sources` 的 jar：

```text
forge/versions/1.20.1/build/libs/bigdogbark-forge-1.20.1-1.1.0.jar
forge/versions/1.21.1/build/libs/bigdogbark-forge-1.21.1-1.1.0.jar
```

## NeoForge 构建

NeoForge 是独立 Gradle 工程，目录为 `neoforge/`：

```bash
cd neoforge
./gradlew build
```

当前 NeoForge 工程目标为 Minecraft `1.21.1`，NeoForge `21.1.213`。

NeoForge 产物位置：

```text
neoforge/build/libs/
```

放进游戏 `mods` 文件夹时使用不带 `-sources` 的 jar：

```text
neoforge/build/libs/bigdogbark-neoforge-1.21.1-1.1.0.jar
```
