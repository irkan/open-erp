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
                            <table class="table table-striped- table-bordered table-hover table-checkable" id="kt_table_1">
                                <thead>
                                <tr>
                                    <th>№</th>
                                    <th>ID</th>
                                    <th>Ad</th>
                                    <th>Açıqlama</th>
                                    <th>Qiymət</th>
                                    <th>Barkod</th>
                                    <th>Vəziyyət</th>
                                    <th>Say</th>
                                    <th>Anbar</th>
                                    <th>Əməliyyat</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="t" items="${list}" varStatus="loop">
                                    <tr>
                                        <td>${loop.index + 1}</td>
                                        <td><c:out value="${t.id}" /></td>
                                        <td><c:out value="${t.name}" /></td>
                                        <td><c:out value="${t.description}" /></td>
                                        <td><c:out value="${t.price}" /></td>
                                        <td><c:out value="${t.barcode}" /></td>
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
                                        <td><c:out value="${utl:calculateInventoryAmount(t.actions)}"/>
                                        </td>
                                        <td><c:out value="${t.actions.get(0).warehouse.name}" /></td>
                                        <td nowrap class="text-center">
                                            <c:set var="view" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'view')}"/>
                                            <c:choose>
                                                <c:when test="${view.status}">
                                                    <a href="#" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${view.object.name}"/>">
                                                        <i class="la <c:out value="${view.object.icon}"/>"></i>
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
                                <form:label path="warehouse.name">Anbar</form:label>
                                <form:input path="warehouse.name" cssClass="form-control" readonly="true"/>
                                <form:hidden path="warehouse.id" cssClass="form-control"/>
                                <form:errors path="warehouse.name" cssClass="alert-danger control-label"/>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <form:label path="action.name">Əməliyyat</form:label>
                                <form:input path="action.name" cssClass="form-control" readonly="true"/>
                                <form:hidden path="action.id"/>
                                <form:errors path="action.name" cssClass="alert-danger control-label"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <form:label path="supplier">Tədarükçü</form:label>
                        <form:select  path="supplier" cssClass="custom-select form-control">
                            <form:options items="${suppliers}" itemLabel="name" itemValue="id" />
                        </form:select>
                    </div>
                    <div class="form-group">
                        <form:label path="inventory.name">Ad</form:label>
                        <form:input path="inventory.name" cssClass="form-control" placeholder="Adı daxil edin"/>
                        <form:errors path="inventory.name" cssClass="alert-danger control-label"/>
                    </div>
                    <div class="form-group">
                        <form:label path="inventory.description">Açıqlama</form:label>
                        <form:input path="inventory.description" cssClass="form-control" placeholder="Açıqlamanı daxil edin" />
                        <form:errors path="inventory.description" cssClass="alert-danger control-label"/>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <form:label path="amount">Say</form:label>
                                <div class="input-group" >
                                    <form:input path="amount" cssClass="form-control" placeholder="Say daxil edin"/>
                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calculator"></i>
                                        </span>
                                    </div>
                                </div>
                                <form:errors path="amount" cssClass="alert-danger control-label"/>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <form:label path="inventory.price">Qiymət</form:label>
                                <div class="input-group" >
                                    <form:input path="inventory.price" cssClass="form-control" placeholder="Məsələn 14.35 AZN"/>
                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-money"></i>
                                        </span>
                                    </div>
                                </div>
                                <form:errors path="inventory.price" cssClass="alert-danger control-label"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <form:label path="inventory.barcode">Barkod</form:label>
                        <form:input path="inventory.barcode" cssClass="form-control" placeholder="Barkodu daxil edin" readonly="true"/>
                        <form:errors path="inventory.barcode" cssClass="alert-danger control-label"/>
                    </div>
                    <div class="form-group">
                        <label class="kt-checkbox kt-checkbox--brand">
                            <form:checkbox path="inventory.old"/> İşlənmişdir
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




