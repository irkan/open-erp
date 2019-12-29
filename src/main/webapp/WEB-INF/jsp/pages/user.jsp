<%--
  Created by IntelliJ IDEA.
  User: irkan.ahmadov
  Date: 01.09.2019
  Time: 1:22
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="utl" uri="/WEB-INF/tld/Util.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="kt-container  kt-grid__item kt-grid__item--fluid">
    <div class="kt-portlet kt-portlet--mobile">
        <div class="kt-portlet__body">
            <c:choose>
                <c:when test="${not empty list}">
                    <c:set var="changePassword" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'change-password')}"/>
                    <c:set var="edit" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'edit')}"/>
                    <c:set var="delete" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'delete')}"/>
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
                            <tr data="<c:out value="${utl:toJson(t)}" />">
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
                                    <c:if test="${changePassword.status}">
                                        <a href="javascript:changePassword($('#change-password-form'), $('#change-password-modal'), '<c:out value="${t.id}" />', '<c:out value="${t.username}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${changePassword.object.name}"/>">
                                            <i class="la <c:out value="${changePassword.object.icon}"/>"></i>
                                        </a>
                                    </c:if>
                                    <c:if test="${edit.status}">
                                        <a href="javascript:edit($('#form'), '<c:out value="${utl:toJson(t)}" />', 'modal-operation', '<c:out value="${edit.object.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${edit.object.name}"/>">
                                            <i class="<c:out value="${edit.object.icon}"/>"></i>
                                        </a>
                                    </c:if>
                                    <c:if test="${delete.status}">
                                        <a href="javascript:deleteData('<c:out value="${t.id}" />', '<c:out value="${t.username}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${delete.object.name}"/>">
                                            <i class="<c:out value="${delete.object.icon}"/>"></i>
                                        </a>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <div class="row">
                                <div class="col-md-12 text-center" style="letter-spacing: 10px;">
                                    Məlumat yoxdur
                                </div>
                            </div>
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
                        <form:label path="employee">Əməkdaş</form:label>
                        <form:select  path="employee" cssClass="custom-select form-control select2-single" multiple="single">
                            <c:forEach var="itemGroup" items="${employees}" varStatus="itemGroupIndex">
                                <optgroup label="${itemGroup.key}">
                                    <form:options items="${itemGroup.value}" itemLabel="person.fullName" itemValue="id"/>
                                </optgroup>
                            </c:forEach>
                        </form:select>
                        <form:errors path="employee" cssClass="control-label alert-danger"/>
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
                    <div class="form-group">
                        <form:label path="userDetail.language">Sistemin dili</form:label>
                        <div>
                            <select name="userDetail.language" id="userDetail.language" class="selectpicker form-control" data-width="fit">
                                <c:forEach var="t" items="${languages}" varStatus="loop">
                                    <option value="<c:out value="${t.attr1}"/>"
                                            data-content='<span class="flag-icon kt-header__topbar-icon">
                                        <img style="height:20px;" src="<c:out value="/assets/media/flags/${t.attr2}" />"
                                        alt="<c:out value="${t.attr1}"/>"></span> <c:out value="${t.name}"/>'>
                                        <c:out value="${t.name}"/>
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <form:errors path="userDetail.language" cssClass="control-label alert alert-danger" />
                    </div>
                </form:form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="submit($('#form'));">Yadda saxla</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Bağla</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="change-password-modal" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Şifrənin yenilənməsi</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="form" id="change-password-form" method="post" action="/admin/user/change-password" cssClass="form-group">
                    <form:hidden path="id"/>
                    <div class="form-group text-center">
                        <label id="username_label"></label>
                    </div>
                    <div class="form-group">
                        <form:label path="password">Şifrə</form:label>
                        <form:password path="password" cssClass="form-control" placeholder="Şifrəni daxil edin"/>
                        <form:errors path="password" cssClass="alert-danger control-label"/>
                    </div>
                </form:form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="submit($('#change-password-form'));">İcra et</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Bağla</button>
            </div>
        </div>
    </div>
</div>


<script>
    $('.select2-single').select2({
        placeholder: "Əməkdaşı seçin",
        allowClear: true
    });
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

    $(function(){
        $('.selectpicker').selectpicker();
    });

    function changePassword(form, modal, id, username){
        $(form).find("#id").val(id);
        $(form).find("#username_label").text(username);
        $(modal).modal('toggle');
    }

    <c:if test="${edit.status}">
    $('#kt_table_1 tbody').on('dblclick', 'tr', function () {
        edit($('#form'), $(this).attr('data'), 'modal-operation', 'Redaktə');
    });
    </c:if>

</script>