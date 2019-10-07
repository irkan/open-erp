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
<%@ taglib prefix="utl" uri="/WEB-INF/tld/Util.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="kt-container  kt-grid__item kt-grid__item--fluid">
    <div class="row">

        <div class="col-lg-12">
            <div class="kt-portlet kt-portlet--mobile">
                <div class="kt-portlet__body">
                    <c:choose>
                        <c:when test="${not empty list}">
                            <table class="table table-striped- table-bordered table-hover table-checkable" id="group_table">
                                <thead>
                                <tr>
                                    <th>№</th>
                                    <th>ID</th>
                                    <th>Qrup</th>
                                    <th>Ad</th>
                                    <th>Açıqlama</th>
                                    <th>Barkod</th>
                                    <th>Vəziyyət</th>
                                    <th>Miqdar</th>
                                    <th>Anbar</th>
                                    <th>Tədarükçü</th>
                                    <th>Status</th>
                                    <th>Əməliyyat</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="t" items="${list}" varStatus="loop">
                                    <tr>
                                        <td>${loop.index + 1}</td>
                                        <td><c:out value="${t.id}" /></td>
                                        <td><c:out value="${t.group.name}" /></td>
                                        <td><c:out value="${t.name}" /></td>
                                        <td><c:out value="${t.description}" /></td>
                                        <td><c:out value="${t.barcode}" /> <%--<c:out value="${utl:toJson(t)}" /> --%> </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${t.old}">
                                                    <span class="kt-badge kt-badge--danger kt-badge--inline kt-badge--pill">İşlənmiş</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="kt-badge kt-badge--success kt-badge--inline kt-badge--pill">Yeni</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td><c:out value="${utl:calculateInventoryAmount(t.actions)}"/> ədəd
                                        </td>
                                        <td><c:out value="${t.actions.get(0).warehouse.name}" /></td>
                                        <td><c:out value="${t.actions.get(0).supplier.name}" /></td>
                                        <td>
                                            <span class="kt-badge kt-badge--success kt-badge--dot"></span>
                                            <span class="kt-badge kt-badge--success"><i class="flaticon2-checkmark"></i></span>
                                            <span class="kt-font-bold kt-font-danger">Təsdiqlənməyib</span>
                                        </td>
                                        <td nowrap class="text-center">
                                            <c:set var="view" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'view')}"/>
                                            <c:choose>
                                                <c:when test="${view.status}">
                                                    <a href="#" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${view.object.name}"/>">
                                                        <i class="la <c:out value="${view.object.icon}"/>"></i>
                                                    </a>
                                                </c:when>
                                            </c:choose>
                                            <c:set var="actions" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'actions')}"/>
                                            <c:choose>
                                                <c:when test="${actions.status}">
                                                    <a href="/warehouse/action/<c:out value="${t.id}"/>" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${actions.object.name}"/>">
                                                        <i class="la <c:out value="${actions.object.icon}"/>"></i>
                                                    </a>
                                                </c:when>
                                            </c:choose>
                                            <c:set var="edit" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'edit')}"/>
                                            <c:choose>
                                                <c:when test="${edit.status}">
                                                    <a href="javascript:edit($('#form'), '<c:out value="${utl:toJson(t)}" />', 'modal-operation', '<c:out value="${edit.object.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${edit.object.name}"/>">
                                                        <i class="<c:out value="${edit.object.icon}"/>"></i>
                                                    </a>
                                                </c:when>
                                            </c:choose>
                                            <c:set var="delete" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'delete')}"/>
                                            <c:choose>
                                                <c:when test="${delete.status}">
                                                    <a href="javascript:deleteData('<c:out value="${t.id}" />', '<c:out value="${t.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${delete.object.name}"/>">
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

    </div>
</div>

<div class="modal fade" id="modal-operation" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Yeni sorğu yarat</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="form" id="form" method="post" action="/warehouse/inventory" cssClass="form-group">
                    <form:hidden path="id"/>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <form:label path="action.warehouse.name">Anbar</form:label>
                                <form:input path="action.warehouse.name" cssClass="form-control" readonly="true"/>
                                <form:hidden path="action.warehouse.id" cssClass="form-control"/>
                                <form:errors path="action.warehouse.name" cssClass="alert-danger control-label"/>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <form:label path="action.action.name">Əməliyyat</form:label>
                                <form:input path="action.action.name" cssClass="form-control" readonly="true"/>
                                <form:hidden path="action.action.id"/>
                                <form:errors path="action.action.name" cssClass="alert-danger control-label"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <form:label path="group">Qrup</form:label>
                                <form:select  path="group" cssClass="custom-select form-control">
                                    <form:options items="${inventory_groups}" itemLabel="name" itemValue="id" />
                                </form:select>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <form:label path="action.supplier">Tədarükçü</form:label>
                                <form:select  path="action.supplier" cssClass="custom-select form-control">
                                    <form:options items="${suppliers}" itemLabel="name" itemValue="id" />
                                </form:select>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <form:label path="name">Ad</form:label>
                        <form:input path="name" cssClass="form-control" placeholder="Adı daxil edin"/>
                        <form:errors path="name" cssClass="alert-danger control-label"/>
                    </div>
                    <div class="form-group">
                        <form:label path="description">Açıqlama</form:label>
                        <form:textarea path="description" cssClass="form-control" placeholder="Açıqlamanı daxil edin" />
                        <form:errors path="description" cssClass="alert-danger control-label"/>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <form:label path="action.amount">Say</form:label>
                                <div class="input-group" >
                                    <form:input path="action.amount" cssClass="form-control" placeholder="Say daxil edin"/>
                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calculator"></i>
                                        </span>
                                    </div>
                                </div>
                                <form:errors path="action.amount" cssClass="alert-danger control-label"/>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <form:label path="barcode">Barkod</form:label>
                                <form:input path="barcode" cssClass="form-control" placeholder="Barkodu daxil edin" readonly="true"/>
                                <form:errors path="barcode" cssClass="alert-danger control-label"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="kt-checkbox kt-checkbox--brand">
                            <form:checkbox path="old"/> İşlənmişdir
                            <span></span>
                        </label>
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

