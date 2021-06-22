var NotificationChannelSettings = function() {
};

NotificationChannelSettings.ERROR_CHANNEL_DOES_NOT_EXIST = 0;
NotificationChannelSettings.ERROR_UNSUPPORTED_SDK = 1;

NotificationChannelSettings.getCanBypassDnd = function(channelName, onSuccess, onFail) {
	cordova.exec(onSuccess, onFail, "NotificationChannelSettings", "getCanBypassDnd", [channelName]);
};

NotificationChannelSettings.openSettings = function (channelName, onSuccess, onFail) {
	cordova.exec(onSuccess, onFail, "NotificationChannelSettings", "openSettings", [channelName]);
}

module.exports = NotificationChannelSettings;
