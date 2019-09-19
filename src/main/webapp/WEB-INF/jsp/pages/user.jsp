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
<%@ taglib prefix="uj" uri="/WEB-INF/tld/UtilJson.tld"%>
<%@ taglib prefix="ua" uri="/WEB-INF/tld/UserAccess.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="kt-container  kt-grid__item kt-grid__item--fluid">
    <div class="kt-portlet kt-portlet--mobile">
        <div class="kt-portlet__body">
            <c:choose>
                <c:when test="${not empty list}">
                    <table class="table table-striped- table-bordered table-hover table-checkable" id="kt_table_1">
                        <thead>
                        <tr>
                            <th>№</th>
                            <th>ID</th>
                            <th>İstifadəçi adı</th>
                            <th>Əməkdaş</th>
                            <th>Flial</th>
                            <th>Email xəbərdarlıq</th>
                            <th>Sms xəbərdarlıq</th>
                            <th>Status</th>
                            <th>Əməliyyat</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="t" items="${list}" varStatus="loop">
                            <tr>
                                <td>${loop.index + 1}</td>
                                <td><c:out value="${t.id}" /></td>
                                <td><c:out value="${t.username}" /></td>
                                <td><c:out value="${t.employee.person.firstName}"/> <c:out value="${t.employee.person.lastName}"/> <c:out value="${t.employee.person.fatherName}"/></td>
                                <td><c:out value="${t.employee.organization.name}" /></td>
                                <c:choose>
                                    <c:when test="${t.userDetail.emailNotification}">
                                        <td>
                                            <span class="kt-badge kt-badge--success kt-badge--inline kt-badge--pill">Aktivdir</span>
                                        </td>
                                    </c:when>
                                    <c:otherwise>
                                        <td>
                                            <span class="kt-badge kt-badge--danger kt-badge--inline kt-badge--pill">Aktiv deyil</span>
                                        </td>
                                    </c:otherwise>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${t.userDetail.smsNotification}">
                                        <td>
                                            <span class="kt-badge kt-badge--success kt-badge--inline kt-badge--pill">Aktivdir</span>
                                        </td>
                                    </c:when>
                                    <c:otherwise>
                                        <td>
                                            <span class="kt-badge kt-badge--danger kt-badge--inline kt-badge--pill">Aktiv deyil</span>
                                        </td>
                                    </c:otherwise>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${t.active}">
                                        <td>
                                            <span class="kt-badge kt-badge--success kt-badge--inline kt-badge--pill">Aktivdir</span>
                                        </td>
                                    </c:when>
                                    <c:otherwise>
                                        <td>
                                            <span class="kt-badge kt-badge--danger kt-badge--inline kt-badge--pill">Aktiv deyil</span>
                                        </td>
                                    </c:otherwise>
                                </c:choose>
                                <td nowrap class="text-center">
                                    <c:set var="view" value="${ua:checkOperation(sessionScope.user.userModuleOperations, page, 'view')}"/>
                                    <c:choose>
                                        <c:when test="${view.status}">
                                            <a href="#" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${view.object.name}"/>">
                                                <i class="la <c:out value="${view.object.icon.name}"/>"></i>
                                            </a>
                                        </c:when>
                                    </c:choose>
                                    <c:set var="edit" value="${ua:checkOperation(sessionScope.user.userModuleOperations, page, 'edit')}"/>
                                    <c:choose>
                                        <c:when test="${edit.status}">
                                            <a href="javascript:edit($('#form'), '<c:out value="${uj:toJson(t)}" />', 'modal-operation', '<c:out value="${edit.object.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${edit.object.name}"/>">
                                                <i class="<c:out value="${edit.object.icon}"/>"></i>
                                            </a>
                                        </c:when>
                                    </c:choose>
                                    <c:set var="delete" value="${ua:checkOperation(sessionScope.user.userModuleOperations, page, 'delete')}"/>
                                    <c:choose>
                                        <c:when test="${delete.status}">
                                            <a href="javascript:deleteData('<c:out value="${t.id}" />', '<c:out value="${t.username}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${delete.object.name}"/>">
                                                <i class="<c:out value="${delete.object.icon}"/>"></i>
                                            </a>
                                        </c:when>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    Məlumat yoxdur
                </c:otherwise>
            </c:choose>

        </div>
    </div>
</div>

<div class="modal fade" id="modal-operation" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Yeni istifadəçi yarat</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">

                <form:form modelAttribute="form" id="form" method="post" action="/admin/user" cssClass="form-group">
                    <div class="form-group">
                        <form:label path="employee.organization">Flial</form:label>
                        <form:select  path="employee.organization" cssClass="custom-select form-control" onchange="getSelect(this, '/admin/employee/get/', 'employee')">
                            <form:options itemLabel="Strukturu seçin" itemValue="0" disabled="true" />
                            <form:options items="${organizations}" itemLabel="name" itemValue="id" />
                        </form:select>
                        <form:errors path="employee.organization" cssClass="control-label alert alert-danger" />
                    </div>
                    <div class="form-group">
                        <form:label path="employee">Əməkdaş</form:label>
                        <form:select  path="employee" cssClass="custom-select form-control">
                            <form:options itemLabel="Əməkdaşı seçin" itemValue="0" disabled="true" />
                            <form:options items="${employees}" itemLabel="person.fullName" itemValue="id" />
                        </form:select>
                        <form:errors path="employee" cssClass="control-label alert alert-danger" />
                    </div>
                    <div class="form-group">
                        <form:label path="username">İstifadəçi adı</form:label>
                        <form:input path="username" cssClass="form-control" placeholder="İstifadəçi adını daxil edin" />
                        <form:errors path="username" cssClass="control-label alert alert-danger" />
                    </div>
                    <div class="form-group">
                        <form:label path="password">Şifrə</form:label>
                        <form:password path="password" cssClass="form-control" placeholder="Şifrəni daxil edin" />
                        <form:errors path="password" cssClass="control-label alert alert-danger" />
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="kt-checkbox kt-checkbox--brand">
                                    <form:checkbox path="userDetail.emailNotification"/> Email xəbərdarlıq
                                    <span></span>
                                </label>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="kt-checkbox kt-checkbox--brand">
                                    <form:checkbox path="userDetail.smsNotification"/> Sms xəbərdarlıq
                                    <span></span>
                                </label>
                            </div>
                        </div>
                    </div>
                </form:form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="$('#form').submit()">Yadda saxla</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Bağla</button>
            </div>
        </div>
    </div>
</div>

<script>
    function getSelect(element, url, filledComponentId){
        $.ajax({
            url: url+$(element).val(),
            type: 'POST',
            dataType: 'json',
            beforeSend: function() {

            },
            success: function(data) {
                console.log(data);
                $(filledComponentId).empty();
                $(filledComponentId).find('option').remove();
                var option;
                $.each(data, function(index, item) {
                    option+="<option value='"+item.id+"'>"+item.person.firstName+"</option>";
                });
                $(filledComponentId).append(option);
            },
            complete: function() {

            },
            error: function() {

            }
        });
    }
</script>