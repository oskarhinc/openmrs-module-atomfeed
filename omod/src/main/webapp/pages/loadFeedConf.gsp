<%
    ui.decorateWith("appui", "standardEmrPage", [ title: ui.message("atomfeed.configuration.label") ])
    ui.includeJavascript("atomfeed", "atomfeed.js")

%>


<script type="text/javascript">
    var breadcrumbs = [
        { icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm' },
        { label: "${ ui.message("atomfeed.label")}",
            link: "${ui.pageLink("atomfeed", "atomfeed")}"
        },
        { label: "${ ui.message('atomfeed.configuration.label') }"}
    ];
</script>

<h2>${ ui.message("atomfeed.configuration.label")}</h2>

<form class="simple-form-ui" method="POST" action="loadFeedConf.page">
    <span id="errorMsg" class="field-error" style="display: none">
        ${ui.message("atomfeed.configuration.errors.invalidJson")}
    </span>
    <span id="server-error-msg" class="field-error" style="display: none">
        ${ui.message("atomfeed.configuration.errors.serverError")}
    </span>
    <input type="hidden" name="action" value="add" />
    <p>
        <label for="json-field">
            <span class="title">
                ${ui.message("atomfeed.configuration.json.label")} (${ ui.message("emr.formValidation.messages.requiredField.label") })
            </span>
        </label>
        <textarea id="json-field" class="required" name="json" rows="15" cols="80"></textarea>
    </p>

    <input type="button" class="cancel" value="${ ui.message("general.cancel") }" onclick="javascript:window.location='/${ contextPath }/atomfeed/atomfeed.page'" />
    <input type="submit" class="confirm right" id="save-button" value="${ ui.message("general.save") }" disabled="disabled" />
</form>