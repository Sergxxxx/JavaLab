<#import "parts/common.ftl" as c>

<@c.page>
List users
<table>
    <thead>
        <tr>
            <th>Name</th>
            <th>Role</th>
            <th></th>
        </tr>
    </thead>
    <body>
    <#list users as user>
    <tr>
        <td>${user.username!"null or missing"}</td>
        <td><#list user.roles as role>${role}<#sep>, </#list></td>
        <td>Registration - ${user.registrationDateTime.format ("dd.MM.yyyy HH:mm:ss")}</td>
        <td><a href="/user/${user.id}">edit</a></td>
    </tr>
    </#list>
    </body>
</table>
</@c.page>