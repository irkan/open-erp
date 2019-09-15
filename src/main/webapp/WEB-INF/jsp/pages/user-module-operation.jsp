<%--
  Created by IntelliJ IDEA.
  User: irkan.ahmadov
  Date: 01.09.2019
  Time: 1:22
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ua" uri="/WEB-INF/tld/UserAccess.tld"%>
<div class="kt-container  kt-grid__item kt-grid__item--fluid">
    <div class="row">
        <div class="col-lg-12">
            <div class="kt-portlet kt-portlet--mobile">
                <div class="kt-portlet__body">
                    <form:form modelAttribute="form" id="form" method="post" action="/admin/get-user-template" cssClass="form-group">
                        <div class="row">
                            <div class="col-md-3 offset-md-3">
                                <select class="custom-select form-control" name="template" id="template" onchange="this.form.submit()">
                                    <option value="0">Şablondan seçin</option>
                                <c:forEach var="t" items="${templates}" varStatus="loop">
                                    <c:choose>
                                        <c:when test="${template_id==t.id}">
                                            <option value="${t.id}" selected>${t.name}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${t.id}">${t.name}</option>
                                        </c:otherwise>
                                    </c:choose>

                                </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-3">
                                <form:select  path="user" cssClass="custom-select form-control" onchange="this.form.submit()">
                                    <form:option value="0" label="İstifadəçini seçin"/>
                                    <form:options items="${users}" itemLabel="employee.person.fullName" itemValue="id"  />
                                </form:select>
                            </div>
                            <div class="col-md-2 offset-md-1 text-right">
                                <button onclick="save()" class="btn btn-primary">Yadda saxla</button>
                            </div>
                        </div>
                        <hr style="width: 100%"/>
                        <c:choose>
                            <c:when test="${not empty list}">
                                <table class="table table-striped- table-bordered table-hover table-checkable" id="module_operation_table">
                                    <thead>
                                    <tr>
                                        <td style="width: 15px;"></td>
                                        <td style="width: 25%"></td>
                                        <td></td>
                                        <td></td>
                                        <c:forEach var="t" items="${operations}" varStatus="loop">
                                            <td class="text-center" style="width: ${75/operations.size()}%">
                                                <c:out value="${t.name}" />
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="m" items="${modules}" varStatus="loop">
                                        <c:choose>
                                            <c:when test="${not empty m.module}">
                                                <tr>
                                                    <td>${loop.index + 1}</td>
                                                    <td class="text-right"><c:out value="${m.name}" /></td>
                                                    <td><c:out value="${m.module.name}" /></td>
                                                    <td class="text-center" style="width: 8px; background-color: #f7f8fa"><input type="checkbox" onclick="checkedRow(this)"></td>
                                                    <c:forEach var="o" items="${operations}" varStatus="loop">
                                                        <td class="text-center">
                                                            <c:set var="status" value="${ua:checkAccess(list, user_module_operations, template_module_operations, m.id, o.id)}"/>
                                                            <c:choose>
                                                                <c:when test="${status.object ne 0 && not empty status.object}">
                                                                    <c:choose>
                                                                        <c:when test="${status.status}">
                                                                            <form:checkbox path="moduleOperations" value="${status.object}" checked="checked"/>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <form:checkbox path="moduleOperations" value="${status.object}"/>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </c:when>
                                                            </c:choose>
                                                        </td>
                                                    </c:forEach>
                                                </tr>
                                            </c:when>
                                        </c:choose>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </c:when>
                            <c:otherwise>
                                Məlumat yoxdur
                            </c:otherwise>
                        </c:choose>
                    </form:form>
                </div>
            </div>
        </div>

    </div>
</div>

<script src="<c:url value="/assets/js/demo4/pages/crud/datatables/advanced/row-grouping.js" />" type="text/javascript"></script>
<script>
    function checkedRow(element){
        let row = $(element).closest("tr");
        if($(element).prop("checked") == true){
            $(row).find('input[type="checkbox"]').each(function(i, chk) {
                $(chk).attr('checked', 'checked');
            });
        } else {
            $(row).find('input[type="checkbox"]').each(function(i, chk) {
                $(chk).removeAttr('checked', 'checked');
            });
        }
    }

    function save() {
        $("#form").attr("action", "/admin/user-module-operation");
        $("#form").submit();
    }
</script>