# WereWolf

------

## 開発用メモ

参考 : https://qiita.com/rushuu_r/items/677bf24db821838a7569

DiscordのBotに関して : https://qiita.com/itsu_dev/items/825e4ed32bf32e4b64b2

### プラグイン読み込み時

- ログ : WereWolf Pluginが読み込まれました



- Discord Bot 起動

### 募集時

- op : /wolf_host
- チャット(global) : 人狼ゲームの募集が開始されました ここを押して参加
- 画面(global) : 「人狼ゲームの募集が開始されました」「/wolf join で参加」



- 参加プレイヤーの名前はString型の連想配列(String Name, String Job = null)"PlayerData"へ入れられる

### 参加時

- Player : /wolf join
- チャット(private) : ゲームに参加しました。
- 画面(private) : 「ゲームに参加しました」「ゲームの開始を待ってください」



- pvp : false
- 全員アドベンチャー
- ロビーへtp

###  

### 準備時(募集締め切り時)

- op : /wolf_ready
- チャット(global) : 募集が締め切られました
- チャット(player) : ゲームの準備をしています
- 画面(global) : 「募集が締め切られました」
- 画面(player) : 「募集が締め切られました」「ゲームの準備をしています」



- フィールドにtp
- スコアボード(n人)

### 開始時

- op : /wolf_start
- チャット(player) : 人狼ゲームが開始されました
- チャット(private) : あなたの役職は です
- 画面(player) : 「人狼ゲーム 開始」



- pvp : true
- discord : ボイチャ参加
- アイテム支給
- 無敵時間10秒(bossbar)
- scoreboad追加

### JobEvent時

- /wolf job

### デス時

- チャット(private) : あなたは死亡しました
- 画面(private) : あなたは死亡しました



- スペクテーター
- ロビーへtp
- 墓VCへ
- Discord : Mute



- ジャッジ判定

### リセット時

- /wolf reset

### 終了時

- 全員スペクテーターモード