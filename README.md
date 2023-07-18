# DiscordBotRandomPick

【福井大学 工学部 電気電子工学科 情報コース 2023年度 前期 オブジェクト指向言語I 自由開発課題】

DiscordBotを作成しました。

botをサーバーに追加する方法については割愛しますが、実行する際ルートディレクトリにsettings.confを配置してください。1行目にトークンをそのまま記載して、2行目に使いたい言語を記載したtxtファイルを作成して拡張子をリネームすればOKです。  
また、Textフォルダにピックリストをテキストファイルで配置してください。そのファイル名とファイル内容にbotがアクセスします。  
サンプルファイル(VALORANTより各種引用)が同封してあります。そのまま使えます。更新したい場合もテキストファイルを書き換えて再ロードだけでOKです。  
Languagesフォルダには言語ファイルが入ります。デフォルトで日本語と英語に対応しています。ファイル名はファイル名(英語).langですが、settings.confに記載するのは「ファイル名(英語)」の部分だけです。

ライブラリは、"net.dv8tion:JDA:5.0.0-beta.12"を使用しています。

有効なコマンドは次の通りです(日本語の場合)。  
!ロード: ファイルを読み込みます  
!ファイル一覧: 読み込めるファイルの一覧を表示します  
!リスト: 読み込まれているファイルの中身を表示します  
!ファイル名: 読み込まれているファイル名を表示します  
!ピック: 指定数ランダムにピックします  
!ヘルプ: コマンドリストを表示します  
!終了: ボットを終了します

言語を追加する場合や英語を使用する場合はLanguageフォルダ内のxxx.langの中身を参照してください。また、追加するときのフォーマットは日本語に従ってください。

# UpdateLog
20230704 アップロード

20230711 各種ファイルの場所わけ、多言語対応
