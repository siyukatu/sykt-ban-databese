# このプラグインについて
![](https://ci.night-fox.space/job/sykt-ban-databese/badge/icon) <br>
しゆかつサーバーでBANされてる人があなたのサーバーに入れなくなるやつです<br>
ちなみに、パーミッション`com.siyukatu.bans.ban`を持っている、もしくはコンフィグファイルに入れとけばその人はあなたのサーバーに入れます<br>

1. [x] コマンドの実装
2. [x] コンフィグの実装
3. [x] Bungeecordの対応
4. [ ] プラグインのapi

## コマンド
コマンドの一覧です。
```text
/ban-database info <user_name> - データベースに保存されているものからユーザー名に指定されたユーザーがBanされている確認します
/ban-database reload - リロードをします
/ban-database help - ヘルプを取得します
```
## コンフィグファイル

以下はコンフィグファイルの設定例です。<br>
"\plugins\ban-database\config.yml"内で編集できます。
```yaml
api_key: "" #apiキーです。[マイページ](http://siyukatu.com/mypage/)から引っ張ってきてください
req_delay: 20 # apiリクエストの間隔を指定します (秒)
whitelist:
  - ""
# ホワイトリストです。パーミッション"com.siyukatu.bans.ban"と同じようにbanを回避できます
debug_level: "info" # デバックレベルです。　"info"から、"debug"に変更すると詳細が見ることができます
```

## 動作保証
現在動作が確認できているものは、Bukkit v1.12.2 以降及びBungeecord v1.12以降です。

## 報告
プラグインの不具合については、[こちらを](https://github.com/siyukatu/sykt-ban-databese)、ご確認ください。
