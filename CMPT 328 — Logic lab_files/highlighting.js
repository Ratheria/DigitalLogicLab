"use strict";

(function() {
    var highlightRegexes = {
        "java": {
            //"block-comment": /\/\*[\s\S]*?!(\*\/)\*\//mg,
            "block-comment": /\/\*[\s\S]*?\*\//mg,
            "line-comment": /\/\/[^\n\r$]*[\n\r$]/g,
            "string": /"((\\")|([^"]))*"/g,
            "type": /\b((byte)|(short)|(int)|(long)|(float)|(double)|(char)|(boolean))\b/g,
            "keyword": /\b((abstract)|(assert)|(break)|(case)|(catch)|(class)|(const)|(continue)|(default)|(do)|(else)|(enum)|(extends)|(final)|(finally)|(for)|(goto)|(if)|(implements)|(import)|(instanceof)|(interface)|(native)|(new)|(package)|(private)|(protected)|(public)|(return)|(static)|(strictfp)|(super)|(switch)|(synchronized)|(this)|(throw)|(throws)|(transient)|(try)|(void)|(volatile)|(while))\b/g,
            "number": /\b[\+\-]?((\d+)|(\d+\.\d*)|(\d*\.\d+))([eE]([\+\-]?)\d+)?\b/g,
        },
 
        // Note: written offline without Python reference (incomplete!)
        "python": {
            "line-comment": /#[^\n\r$]*[\n\r$]/g,
            "string": /("((\\")|([^"]))*")|('((\\')|([^']))*')/g,
            "type": /\b((int)|(float)|(bool)|(str)|(list)|(set)|(dict))\b/g,
            "keyword": /\b((class)|(def)|(for)|(from)|(if)|(import)|(return)|(self))\b/g,
            "number": /\b[\+\-]?((\d+)|(\d+\.\d*)|(\d*\.\d+))([eE]([\+\-]?)\d+)?\b/g
        }
    };
    
    function highlight(region) {
        if (region.dataset.language in highlightRegexes) {
            var regexes = highlightRegexes[region.dataset.language];
            Object.keys(regexes).forEach(function(clazz) {
                highlightRegex(region, clazz, regexes[clazz]);
            });
        }
    }
    
    function highlightRegex(region, clazz, regex) {
        for (var i = 0; i < region.childNodes.length; ++i) {
            var node = region.childNodes[i];
            if (node.nodeType === HTMLElement.TEXT_NODE) {
                var text = node.textContent;
                var matches = matchRegexAll(text, regex);
                
                if (matches.length > 0) {
                    var nodesAdded = applyMatches(region, i, clazz, matches);
                    i += nodesAdded;
                }
            }
        }
    }
    
    function matchRegexAll(text, regex) {
        var matches = [];
        var m;
        do {
            m = regex.exec(text);
            if (m)
                matches.push([m.index, m[0].length]);
        } while (m);
        
        return matches;
    }
    
    function applyMatches(region, textNodeIndex, clazz, matches) {
        var lastMatch = matches[matches.length - 1];
        var matchIndex = lastMatch[0],
            matchLength = lastMatch[1];
        var newNodes = 0;
        var span = document.createElement("span");
        span.classList.add(clazz);
        var textNode = region.childNodes[textNodeIndex];
        span.innerText = textNode.textContent.substring(matchIndex, matchIndex + matchLength);
        region.insertBefore(span, region.childNodes[textNodeIndex + 1]);
        ++newNodes;
        
        // Is there any text after this match that we need to preserve?
        if (textNode.textContent.length > matchIndex + matchLength) {
            var endingText = textNode.textContent.substring(matchIndex + matchLength);
            var newTextNode = document.createTextNode(endingText);
            region.insertBefore(newTextNode, region.childNodes[textNodeIndex + 2]);
            ++newNodes;
        }
        
        textNode.textContent = textNode.textContent.substring(0, matchIndex);
        
        if (matches.length > 1)
            return newNodes + applyMatches(region, textNodeIndex, clazz, matches.slice(0, -1));        
        else
            return newNodes;
    }
    
    var highlightRegions = document.querySelectorAll("code.highlight");
    highlightRegions.forEach(function(r) { highlight(r); });
})();
