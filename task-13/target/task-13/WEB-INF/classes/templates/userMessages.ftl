<#import "parts/common.ftl" as c>

<@c.page>
    <div class="form-row">
        <div class="form-group col-md-2">
            <form class="form-inline" method="get" action="/sortDesc/${user.getId()}">
                <button class="btn btn-info ml-2" type="submit">Sort by desc</button>
            </form>
        </div>
        <div class="form-group col-md-2">
            <form class="form-inline" method="get" action="/sortAsc/${user.getId()}">
                <button class="btn btn-info ml-2" type="submit">Sort by asc</button>
            </form>
        </div>
    </div>

    <#if isCurrentUser && message??>
        <#include "parts/messageEdit.ftl" />
    </#if>

    <#include "parts/messageList.ftl" />
</@c.page>