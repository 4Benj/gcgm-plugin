# GCGM - Grasscutter Game Master Dashboard
GCGM is the first [Grasscutter](https://github.com/Grasscutters/Grasscutter) plugin (Apart from Magix's test plugin) and it's goal is to implement a dashboard into the dispatch server so that game masters/server administrators can easily administor their server.

## Currently Planned Features: 
- [x] Loading basic web page
- [ ] Nice looking CSS
- [ ] Server performance stats
- [ ] See players registered to dispatch server
- [ ] See players currently online
- [ ] Send mail to all players
- [ ] Give players items
- [ ] Summon enemies near players

The features listed are to achieve an MVP for the first release.

## Important Notes:
This plugin is made to run on the current [Development](https://github.com/Grasscutters/Grasscutter/tree/development) branch of Grasscutter. \
This plugin is in very early development and the web dashboard only displays the default react webpage. \
**If you require support please ask on the [Grasscutter Discord](https://discord.gg/T5vZU6UyeG). However, support is not guarenteed.**

## Setup 
### Download Plugin Jar
Coming soon!

### Compile yourself
1. Pull the latest code from github using ``git clone https://github.com/Grasscutters/gcgm-plugin`` in your terminal of choice.
2. Navigate into the newly created ``gcgm-plugin`` folder and run ``gradlew build`` (cmd) **or** ``./gradlew build`` (Powershell, Linux & Mac).
3. Assuming the build succeeded, in your file explorer navigate to the ``gc-plugin`` folder, you should have a ``gcgm-plugin.jar`` file, copy it.
4. Navigate to your ``Grasscutter`` server, find the ``plugins`` folder and paste the ``gcgm-plugin.jar`` into it. 
5. Start your server.
6. Your server should then start and after a few seconds, you should be greated with these messages and the server will quit.
```
[WARN] The './plugins/gcgm/www' folder does not exist.
[WARN] Please extract the contents of 'DefaultWebApp.zip' from within './plugins/gcgm' to './plugins/gcgm/www
[WARN] Your server will now exit to allow this process to be completed
```
7.  Inside the ``plugins`` folder there now should be a new folder with the name of ``gcgm``, when you open the folder, there should be a ``.zip`` file called ``DefaultWebApp.zip``. Extract the contents of this zip into a folder called ``www``.

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