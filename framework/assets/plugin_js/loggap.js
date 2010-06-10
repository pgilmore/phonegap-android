/**
 * Assigns debug and console functions to the native functions.
 * Overrides the normal console junk
 */
function Console() {
	
}

Console.prototype.debug = function(msg) {
	if (typeof LogGap != "undefined") {
		LogGap.debug(msg);
	} else {
		alert('Debug: ' + msg);
	}
};

Console.prototype.log = function(msg) {
	if (typeof LogGap != "undefined") {
		LogGap.log(msg);
	} else {
		alert('Console: ' + msg);
	}
};

PhoneGap.addConstructor(function() {
    if (typeof console == "undefined" || typeof console != "Console") console = new Console();
});

