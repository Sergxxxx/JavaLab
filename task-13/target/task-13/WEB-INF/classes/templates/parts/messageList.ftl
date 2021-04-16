<#include "security.ftl">

<div class="card-columns">
    <#list messages as message>
        <div class="card my-3">
            <#if message.filename??>
                <img class="card-img-top" src="/img/${message.filename}">
            </#if>
            <div class="m-2">
                <span>${message.text}</span><br>
                <i>#${message.tag}</i><br><br>
                <small>Created - ${message.createdDateTime.format ("dd.MM.yyyy HH:mm:ss")}</small><br>
                <small><#if message.updatedDateTime??>Last updated - ${message.updatedDateTime.format ("dd.MM.yyyy HH:mm:ss")}</#if></small>
            </div>
            <div class="card-footer text-muted">
                <a href="/user-messages/${message.author.id}">${message.authorName}</a>
                <#if message.author.id == currentUserId>
                    <a class="btn btn-primary" href="/user-messages/${message.author.id}?message=${message.id}">
                        Edit
                    </a>
                    <a class="btn btn-warning" href="/del-user-messages/${message.author.id}?message=${message.id}">
                        Delete
                    </a>
                </#if>
            </div>
        </div>
    <#else>
        No message
    </#list>
</div>