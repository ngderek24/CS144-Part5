window.onload = function () {
	var oTextbox = new AutoSuggestControl(document.getElementById("query"), new SuggestionProvider());
}

var xmlHttp = new XMLHttpRequest();

AutoSuggestControl.prototype.selectRange = function (start, length) {
	if (this.textbox.createTextRange) {
        var oRange = this.textbox.createTextRange();
        oRange.moveStart("character", start);
        oRange.moveEnd("character", length - this.textbox.value.length);
        oRange.select();
    } else if (this.textbox.setSelectionRange) {
        this.textbox.setSelectionRange(start, length);
    }

    this.textbox.focus();
};

AutoSuggestControl.prototype.typeAhead = function (suggestion) {
	if (this.textbox.createTextRange || this.textbox.setSelectionRange) {
        var textboxLength = this.textbox.value.length;
        this.textbox.value = suggestion;
        this.selectRange(textboxLength, suggestion.length);
	}
};

AutoSuggestControl.prototype.autosuggest = function (suggestions, typeAhead) {
 	if (suggestions.length > 0) {
 		var firstSuggestion = suggestions[0];
		if (typeAhead && firstSuggestion.length > this.textbox.value.length) {
        	this.typeAhead(firstSuggestion);
    	}
    	this.showSuggestions(suggestions);
    } else {
    	this.hideSuggestions();
    }
};

AutoSuggestControl.prototype.handleKeyUp = function (event) {
	var iKeyCode = event.keyCode;

    if (iKeyCode == 8 || iKeyCode == 46) {
        this.provider.requestSuggestions(this, false);
    } else if (iKeyCode < 32 || (iKeyCode >= 33 && iKeyCode <= 46) || (iKeyCode >= 112 && iKeyCode <= 123)) {
        //ignore
    } else {
    	this.provider.requestSuggestions(this, true);
    }
};

AutoSuggestControl.prototype.handleKeyDown = function (oEvent) {
    switch(oEvent.keyCode) {
        case 38: //up arrow
            this.previousSuggestion();
            break;
        case 40: //down arrow 
            this.nextSuggestion();
            break;
        case 13: //enter
            this.hideSuggestions();
            break;
    }
};

AutoSuggestControl.prototype.init = function () {
	var oThis = this;
    this.textbox.onkeyup = function (oEvent) {
        if (!oEvent) {
            oEvent = window.event;
        }
        oThis.handleKeyUp(oEvent);
    };

    this.textbox.onkeydown = function (oEvent) {
        if (!oEvent) {
            oEvent = window.event;
        } 

        oThis.handleKeyDown(oEvent);
    };

    this.textbox.onblur = function () {
        oThis.hideSuggestions();
    };

    this.createDropDown();
};

function AutoSuggestControl(textbox, provider) {
	this.cur = -1;
	this.layer = null;
	this.provider = provider;
	this.textbox = textbox;
	this.init();
}

AutoSuggestControl.prototype.nextSuggestion = function () {
    var cSuggestionNodes = this.layer.childNodes;

    if (cSuggestionNodes.length > 0 && this.cur < cSuggestionNodes.length-1) {
        var oNode = cSuggestionNodes[++this.cur];
        this.highlightSuggestion(oNode);
        this.textbox.value = oNode.firstChild.nodeValue; 
    }
};

AutoSuggestControl.prototype.previousSuggestion = function () {
    var cSuggestionNodes = this.layer.childNodes;

    if (cSuggestionNodes.length > 0 && this.cur > 0) {
        var oNode = cSuggestionNodes[--this.cur];
        this.highlightSuggestion(oNode);
        this.textbox.value = oNode.firstChild.nodeValue; 
    }
};

AutoSuggestControl.prototype.hideSuggestions = function () {
    this.layer.style.visibility = "hidden";
};

AutoSuggestControl.prototype.highlightSuggestion = function (oSuggestionNode) {
    for (var i=0; i < this.layer.childNodes.length; i++) {
        var oNode = this.layer.childNodes[i];
        if (oNode == oSuggestionNode) {
            oNode.className = "current"
        } else if (oNode.className == "current") {
            oNode.className = "";
        }
    }
};

AutoSuggestControl.prototype.createDropDown = function () {
    this.layer = document.createElement("div");
    this.layer.className = "suggestions";
    this.layer.style.visibility = "hidden";
    this.layer.style.width = this.textbox.offsetWidth;
    document.body.appendChild(this.layer);

    var oThis = this;
    this.layer.onmousedown = this.layer.onmouseup = 
    this.layer.onmouseover = function (oEvent) {
        oEvent = oEvent || window.event;
        oTarget = oEvent.target || oEvent.srcElement;

        if (oEvent.type == "mousedown") {
            oThis.textbox.value = oTarget.firstChild.nodeValue;
            oThis.hideSuggestions();
        } else if (oEvent.type == "mouseover") {
            oThis.highlightSuggestion(oTarget);
        } else {
            oThis.textbox.focus();
        }
    };
};

AutoSuggestControl.prototype.getLeft = function () {
    var oNode = this.textbox;
    var iLeft = 0;

    while(oNode.tagName != "BODY") {
        iLeft += oNode.offsetLeft;
        oNode = oNode.offsetParent; 
    }

    return iLeft;
};

AutoSuggestControl.prototype.getTop = function () {
    var oNode = this.textbox;
    var iTop = 0;

    while(oNode.tagName != "BODY") {
        iTop += oNode.offsetTop;
        oNode = oNode.offsetParent; 
    }

    return iTop;
};

AutoSuggestControl.prototype.showSuggestions = function (aSuggestions) {
	var oDiv = null;
    this.layer.innerHTML = "";

    for (var i=0; i < aSuggestions.length; i++) {
        oDiv = document.createElement("div");
        oDiv.appendChild(document.createTextNode(aSuggestions[i]));
        this.layer.appendChild(oDiv);
    }

    this.layer.style.left = this.getLeft() + "px";
    this.layer.style.top = (this.getTop()+this.textbox.offsetHeight) + "px";
    this.layer.style.visibility = "visible";
};

SuggestionProvider.prototype.requestSuggestions = function (autoSuggestControlObj, typeAhead) {
	var request = "suggest?q=" + encodeURI(autoSuggestControlObj.textbox.value);

	xmlHttp.open("GET", request);
	xmlHttp.onreadystatechange = function () {
		var suggestions = new Array();
		if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
			var xmlDoc = xmlHttp.responseXML;
			if (xmlDoc != undefined) {
				var suggests = xmlDoc.getElementsByTagName("suggestion");
				for (i = 0; i < suggests.length; i++) {
					suggestions[i] = suggests[i].attributes[0].nodeValue;
				}
				
				autoSuggestControlObj.autosuggest(suggestions, typeAhead);
			} else {
				//document.getElementById("suggestion").innerHTML = "";
			}
		}
	};
	xmlHttp.send(null); 
};

function SuggestionProvider() {
	
}