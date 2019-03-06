"use strict";

(function() {
    var toolboxes = document.querySelectorAll("x-toolbox");
    for (var i = 0; i < toolboxes.length; ++i) {
        var toolbox = toolboxes[i];
        if (toolbox.dataset.title !== undefined) {
            var title = document.createElement("x-title");
            title.innerText = toolbox.dataset.title;
            toolbox.insertBefore(title, toolbox.childNodes[0]);

            title.addEventListener("click", function(e) {
                var toolbox = e.target.parentNode;
                if (toolbox.classList.contains("open"))
                    toolbox.classList.remove("open");
                else
                    toolbox.classList.add("open");
            });
        }
    }
})();
