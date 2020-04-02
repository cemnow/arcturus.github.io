# PNGCamera

This IS UPDATED VERSION OF PNGCAMERA. #peace
T

---

Create a Camera Folder structure.

XAMPP Example:\
`C:\xampp\htdocs\swfs\camera` and `C:\xampp\htdocs\swfs\camera\thumbnails`

Change your external_variables to the following:\
`navigator.thumbnail.url_base=https://mydomain.com/swfs/camera/thumbnails/`\
`stories.image_url_base=https://mydomain.com/swfs/camera/`

Download the required [Habbo.swf](https://github.com/uncrypt3d/arcturus.github.io/tree/master/Arcturus-plugins/PNGCamera/UPDATED-PNG/Habbo.swf).


---

### Database Modifications

Launch Arcturus

Once Arcturus is open type `stop` to close, we do this to import the needed SQL modifications.

Go to your Database and open the `emulator_settings` table.

Then edit the following:

`camera.url = https://mydomain.com/swfs/camera/`

`imager.location.output.camera = C:\xampp\htdocs\swfs\camera\`

`imager.location.output.thumbnail = C:\xampp\htdocs\swfs\camera\thumbnails\`

`camera.enabled = 1`

`interaction_type` should be set to `external_image_png` for Camera Pic and Habbo Selfie and `interaction_modes_count` set to `0` in the `items_base` table.

---

This plugin has not been verified by TheGeneral/Wesley12312. Use this at your own risk. Always remember to make backups of your database before installation of third-party plugins.
