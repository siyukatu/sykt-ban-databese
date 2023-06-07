# このプラグインについて <br>
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

## 現在確認済みのバグ
統合版idでのinfoができない - getUUID() でmojangに強制的に問い合わせしているため。 
コンフィグ未設定時にエラーが発生する - null chekをしていないため。 
統合版のxuidに対応していない (geyserのuuid) - こちら側では対応しかねるためこれはsiyukatu氏に確認する。 
これらの問題は可及的速やかに対応するべきである。

## 報告
プラグインの不具合については、[こちらを](https://github.com/siyukatu/sykt-ban-databese)、ご確認ください。
