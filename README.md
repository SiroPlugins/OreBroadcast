# OreBroadcast
[![Download](https://api.bintray.com/packages/siroshun/maven-repo/OreBroadcast/images/download.svg) ](https://bintray.com/siroshun/maven-repo/OreBroadcast/_latestVersion)
![GitHub Workflow Status](https://img.shields.io/github/workflow/status/SiroPlugins/OreBroadcast/Java%20CI)
![GitHub](https://img.shields.io/github/license/SiroPlugins/OreBroadcast)

![Preview](img/logo-ore-broadcast.png)

## プラグインについて

Spigot 1.15 以上で動作する。

鉱石を掘ると、サーバー全体に鉱石の種類と数を通知する。


## 変更点

- Maven の依存関係の変更および更新
- Spigot-API 1.15.2-R0.1-SNAPSHOT API を使用
- コマンド / metrics / update の廃止
- ピストン検知削除
- 変更に伴う config.yml の必要のない記述の削減
- その他 コード改善など

## 記録プラグイン

OreLogger: https://github.com/SiroPlugins/OreLogger

## Maven

```xml
        <repository>
            <id>bintray-siroshun-maven-repo</id>
            <url>https://siroshun.bintray.com/maven-repo</url>
        </repository>
```
```xml
        <dependency>
            <groupId>com.github.siroshun09</groupId>
            <artifactId>orebroadcast</artifactId>
            <version>VERSION</version>
            <scope>provided</scope>
        </dependency>
```

## OreDetectionEvent

プラグインの依存に追加すると、以下のイベントが使用可能になります。

```
OreDetectionEvent

    @NotNull Player getPlayer() - 掘った(発見した)プレイヤー

    @NotNull Set<Block> getVein() - 発見した鉱石の塊

    @NotNull Block getBlockMined() - 掘ったブロック

    @NotNull HandlerList getHandlerList()

    @NotNull HandlerList getHandlers()
```
## License

このプロジェクトは GPL-3.0 のもとで公開しています。詳しくは [ライセンスファイル](LICENSE) をお読みください。

This project is licensed under the permissive GPL-3.0 license. Please see [LICENSE](LICENSE) for more info.

Copyright © 2019-2020, Siroshun09