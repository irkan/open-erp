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
                                    <th></th>
                                    <th>Hesab nömrəsi</th>
                                    <th>Valyuta</th>
                                    <th>Bank</th>
                                    <th>Bank hesab nömrəsi</th>
                                    <th>Bank kodu</th>
                                    <th>Bank swift bik</th>
                                    <th>Balans</th>
                                    <th>Açıqlama</th>
                                    <th>Əməliyyat</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="t" items="${list}" varStatus="loop">
                                    <tr>
                                        <td>${loop.index + 1}</td>
                                        <td><c:out value="${t.id}" /></td>
                                        <td><c:out value="${t.organization.name}"/></td>
                                        <th><c:out value="${t.accountNumber}"/></th>
                                        <td><c:out value="${t.currency}"/></td>
                                        <td><c:out value="${t.bankName}"/></td>
                                        <td><c:out value="${t.bankAccountNumber}"/></td>
                                        <td><c:out value="${t.bankCode}"/></td>
                                        <td><c:out value="${t.bankSwiftBic}"/></td>
                                        <th><c:out value="${t.balance}"/></th>
                                        <td><c:out value="${t.description}"/></td>
                                        <td nowrap class="text-center">
                                            <c:set var="view" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'view')}"/>
                                            <c:choose>
                                                <c:when test="${view.status}">
                                                    <a href="#" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${view.object.name}"/>">
                                                        <i class="la <c:out value="${view.object.icon.name}"/>"></i>
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
                                                    <a href="javascript:deleteData('<c:out value="${t.id}" />', '<c:out value="${t.accountNumber}" /><br/><c:out value="${t.description}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${delete.object.name}"/>">
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
                <form:form modelAttribute="form" id="form" method="post" action="/accounting/account" cssClass="form-group">
                    <div class="form-group">
                        <form:label path="organization">Flial</form:label>
                        <form:select  path="organization" cssClass="custom-select form-control">
                            <form:options items="${organizations}" itemLabel="name" itemValue="id" />
                        </form:select>
                        <form:errors path="organization" cssClass="alert-danger control-label"/>
                    </div>
                    <div class="row">
                        <div class="col-md-9">
                            <div class="form-group">
                                <form:label path="accountNumber">Hesab nömrəsi</form:label>
                                <form:input path="accountNumber" cssClass="form-control account-number" placeholder="Hesab nömrəsi" readonly="true"/>
                                <form:errors path="accountNumber" cssClass="alert-danger control-label"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <form:label path="currency">Valyuta</form:label>
                                <form:select  path="currency" cssClass="custom-select form-control">
                                    <form:options items="${currencies}" itemLabel="name" itemValue="name" />
                                </form:select>
                                <form:errors path="currency" cssClass="alert-danger control-label"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <form:label path="description">Açıqlama</form:label>
                        <form:textarea path="description" cssClass="form-control" placeholder="Açıqlama daxil edin"/>
                        <form:errors path="description" cssClass="alert-danger control-label"/>
                    </div>
                    <div class="form-group">
                        <form:label path="balance">Balans</form:label>
                        <div class="input-group" >
                            <form:input path="balance" cssClass="form-control" placeholder="Balansı daxil edin"/>
                            <div class="input-group-append">
                                                    <span class="input-group-text">
                                                        <i class="la la-usd"></i>
                                                    </span>
                            </div>
                        </div>
                        <form:errors path="balance" cssClass="alert-danger control-label"/>
                    </div>
                    <div class="form-group">
                        <form:label path="bankName">Bank</form:label>
                        <form:input path="bankName" cssClass="form-control" placeholder="Bankınızı daxil edin. Məs: PashaBank"/>
                        <form:errors path="bankName" cssClass="alert-danger control-label"/>
                    </div>
                    <div class="form-group">
                        <form:label path="bankAccountNumber">Bank hesab nömrəsi</form:label>
                        <form:input path="bankAccountNumber" cssClass="form-control uppercase" placeholder="Bank hesab nömrəsini daxil edin"/>
                        <form:errors path="bankAccountNumber" cssClass="alert-danger control-label"/>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <form:label path="bankCode">Bank kodu</form:label>
                                <form:input path="bankCode" cssClass="form-control uppercase" placeholder="Bank kodunu daxil edin" />
                                <form:errors path="bankCode" cssClass="alert-danger control-label"/>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <form:label path="bankSwiftBic">Bank swift bik</form:label>
                                <form:input path="bankSwiftBic" cssClass="form-control uppercase" placeholder="Bank swiftini daxil edin" />
                                <form:errors path="bankSwiftBic" cssClass="alert-danger control-label"/>
                            </div>
                        </div>
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