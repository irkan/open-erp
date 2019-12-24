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
    <div class="row">

        <div class="col-lg-12">
            <div class="kt-portlet kt-portlet--mobile">
                <div class="kt-portlet__body">
                    <c:choose>
                        <c:when test="${not empty list}">
                            <c:set var="detail" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'detail')}"/>
                            <c:set var="delete" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'delete')}"/>
                            <table class="table table-striped- table-bordered table-hover table-checkable" id="group_table">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Struktur</th>
                                    <th>Tarix</th>
                                    <th>Əməkdaş</th>
                                    <th>Say</th>
                                    <th>Açıqlama</th>
                                    <th>Yaradılma tarixi</th>
                                    <th>Əməliyyat</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="t" items="${list}" varStatus="loop">
                                     <tr data="<c:out value="${utl:toJson(t)}" />">
                                         <td><c:out value="${t.id}" /></td>
                                         <th><c:out value="${t.organization.name}" /></th>
                                         <th><fmt:formatDate value = "${t.demonstrateDate}" pattern = "dd.MM.yyyy" /></th>
                                         <th><c:out value="${t.employee.person.fullName}" /></th>
                                         <td><c:out value="${t.amount}" /></td>
                                         <td><c:out value="${t.description}" /></td>
                                         <td><fmt:formatDate value = "${t.createdDate}" pattern = "dd.MM.yyyy HH:mm" /></td>
                                         <td nowrap class="text-center">
                                             <c:if test="${detail.status}">
                                                 <a href="/sale/demonstration-detail/<c:out value="${t.employee.id}"/>" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${detail.object.name}"/>">
                                                     <i class="la <c:out value="${detail.object.icon}"/>"></i>
                                                 </a>
                                             </c:if>
                                             <c:if test="${delete.status}">
                                                 <a href="javascript:deleteData('<c:out value="${t.id}" />', '<fmt:formatDate value = "${t.demonstrateDate}" pattern = "dd.MM.yyyy" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${delete.object.name}"/>">
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

    </div>
</div>

<div class="modal fade" id="modal-operation" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Yeni sorğu yarat</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="form" id="form" method="post" action="/sale/demonstration" cssClass="form-group">
                    <form:hidden path="id"/>
                    <input type="hidden" name="organization" value="<c:out value="${sessionScope.organization.id}"/>"/>
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
                        <form:label path="demonstrateDate">Nümayiş tarixi</form:label>
                        <div class="input-group date" >
                            <form:input path="demonstrateDate" autocomplete="off" date="date" cssClass="form-control datepicker-element" placeholder="dd.MM.yyyy"/>
                            <div class="input-group-append">
                                <span class="input-group-text">
                                    <i class="la la-calendar"></i>
                                </span>
                            </div>
                        </div>
                        <form:errors path="demonstrateDate" cssClass="control-label alert-danger" />
                    </div>
                    <div class="form-group">
                        <form:label path="amount">Say</form:label>
                        <div class="input-group date" >
                            <form:input path="amount" autocomplete="off" cssClass="form-control" placeholder="Sayı daxil edin"/>
                            <div class="input-group-append">
                                    <span class="input-group-text">
                                        <i class="la la-calculator"></i>
                                    </span>
                            </div>
                        </div>
                        <form:errors path="amount" cssClass="control-label alert-danger" />
                    </div>
                    <div class="form-group">
                        <form:label path="description">Açıqlama</form:label>
                        <form:textarea path="description" cssClass="form-control"/>
                        <form:errors path="description" cssClass="alert-danger control-label"/>
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

<script src="<c:url value="/assets/js/demo4/pages/crud/datatables/advanced/row-grouping.js" />" type="text/javascript"></script>

<script>
    <c:if test="${edit.status}">
    $('#group_table tbody').on('dblclick', 'tr', function () {
        edit($('#form'), $(this).attr('data'), 'modal-operation', 'Redaktə');
    });
    </c:if>
</script>


