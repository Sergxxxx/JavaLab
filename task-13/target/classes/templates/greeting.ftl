<#import "parts/common.ftl" as c>

<@c.page>
<h5>Hello, user</h5>
<div>This is blog application!</div>
    <div class="card-columns">
        <#list messages as message>
            <div class="card my-3">
                <div class="m-2">
                    <span>${message.text}</span><br>
                    <i>#${message.tag}</i><br><br>
                    <small>Created - ${message.createdDateTime.format ("dd.MM.yyyy HH:mm:ss")}</small><br>
                </div>
                <div class="card-footer text-muted">${message.authorName}</div>
            </div>
        <#else>
            No message
        </#list>
    </div>
</@c.page>