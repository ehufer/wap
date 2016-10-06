/*global $, console */
(function () {
    'use strict';
    
    var currentTermAutocomplete = "";
    
    
    function setAutocomplete(jsonData) {
        var availableTags = jsonData.terms;
        $("#term").autocomplete({
            source: availableTags,
            select: function (event, ui) {
                $("#search").click();
            }
        });
    }

    function errorHandler(xhr, status, exception) {
        console.log("Error Ajax, Status: " + status + " execption: " + exception);
    }
    
    function showDefinitions(jsonWordDefinitions) {
        $("#termRequested").html(jsonWordDefinitions.word);
        var currentType = "$$$$";
        jsonWordDefinitions.definitions.forEach(function (def) {
            if (currentType !== def.type) {
                currentType = def.type;
                $("#definitions").append("<dt>" + def.type + "</dt>");
            }
            $("#definitions").append("<dd>" + def.definition + "</dd>");
        });
    }
    
    function searchClickHandler() {
        $("#termRequested").html("");
        $("#definitions").html("");
        $("#loader").show();
        $.get("http://localhost:8080/WAP_Project/dictionary", {'term': $("#term").val()})
            .done(showDefinitions)
            .fail(errorHandler)
            .always(function () {
                $("#loader").hide();
            });
        $("#term").autocomplete("close");
    }

    function termKeyPressHandler(event) {
        if (event.which === 13) {
            $("#search").click();
        } else {
            var currentTermVal = $("#term").val() + event.key;
            $.get("http://localhost:8080/WAP_Project/dictionary", { begins: currentTermVal})
                .done(setAutocomplete)
                .fail(errorHandler);
        }
    }
    
    function load() {
        $("#term").keypress(termKeyPressHandler).focus();
        $("#search").click(searchClickHandler);
    }
    
    $(load);
})();