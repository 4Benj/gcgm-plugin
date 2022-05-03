# GCGM - Grasscutter Game Master Dashboard
GCGM is the first [Grasscutter](https://github.com/Grasscutters/Grasscutter) plugin (Apart from Magix's test plugin) and it's goal is to implement a dashboard into the dispatch server so that game masters/server administrators can easily administor their server.

## Currently Planned Features: 
- [x] Loading basic web page
- [x] Nice looking CSS
- [x] Websockets (Although it is very basic)
- [ ] Widgets
- [x] Server performance stats
- [ ] See players registered to dispatch server
- [ ] See players currently online
- [ ] Send mail to all players
- [ ] Give players items
- [ ] Summon enemies near players

The features listed are to achieve an MVP for the first release.

## Important Notes:
This plugin is made to run on the current [Development](https://github.com/Grasscutters/Grasscutter/tree/development) branch of Grasscutter. \
This plugin is in very early development and the web dashboard only displays a side panel and a performance graph. \
**If you require support please ask on the [Grasscutter Discord](https://discord.gg/T5vZU6UyeG). However, support is not guaranteed.** \
**If you encounter any issues, please report them on the [issue tracker](https://github.com/Grasscutters/gcgm-plugin/issues). However, please search to see if anyone else has encountered your issue before. Any duplicate issues will be closed.**
<h3 style="margin:0;padding:0;">THE ISSUE TRACKER IS NOT A SUPPORT FORM.</h3>

## Setup 
### Download Plugin Jar
Coming soon!

### Compile yourself
1. Pull the latest code from github using ``git clone https://github.com/Grasscutters/gcgm-plugin`` in your terminal of choice.
2. Locate your grasscutter server and copy the ``grasscutter`` server jar into the newly created ``gcgm-plugin/gc-plugin/lib`` folder
3. Navigate back into the project root folder called ``gcgm-plugin`` folder and run ``gradlew build`` (cmd) **or** ``./gradlew build`` (Powershell, Linux & Mac).
4. Assuming the build succeeded, in your file explorer navigate to the ``gc-plugin`` folder, you should have a ``gcgm-plugin.jar`` file, copy it.
5. Navigate to your ``Grasscutter`` server, find the ``plugins`` folder and paste the ``gcgm-plugin.jar`` into it. 
6. Start your server.
7. Your server should then start and after a few seconds, you should be greated with these messages.
```
[WARN] [GCGM] The './plugins/gcgm/www' folder does not exist.
[INFO] [GCGM] Copying 'DefaultWebApp.zip' to './plugins/GCGM'
[WARN] [GCGM] Please extract the contents of 'DefaultWebApp.zip' from within './plugins/GCGM' to './plugins/GCGM/www
[WARN] [GCGM] GCGM has now loaded...
...
[ERROR] [GCGM] GCGM could not find the 'www' folder inside its plugin directory
[ERROR] [GCGM] Please make sure a 'www' folder exists by extracting 'DefaultWebApp.zip' into a new 'www' or download a third-party dashboard
[ERROR] [GCGM] GCGM could not be enabled

```
8. Type ``stop`` to stop the server
9. Inside the ``plugins`` folder there now should be a new folder with the name of ``gcgm``, when you open the folder, there should be file called ``DefaultWebApp.zip``. Extract the contents of this zip into a folder called ``www``.
10. Start your server

Your final plugins folder's directory structure should look similar to this
```
plugins
│   gcgm-plugin.jar
│   ...
└───gcgm
    │   DefaultWebApp.zip
    │
    └───www
        │   asset-manifest.json
        │   favicon.ico
        │   index.html
        │   logo192.png
        │   logo512.png
        │   manifest.json
        │   robot.txt
        └───static
            └───css
            │   ...
            └───js
            │   ...
            └───media
            │   ...
```