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
                                    <th>Hesab nömrəsi</th>
                                    <th>Açıqlama</th>
                                    <th>Tarix</th>
                                    <th>Miqdar</th>
                                    <th>Qiymət</th>
                                    <th>Kurs</th>
                                    <th>Ümumi qiymət</th>
                                    <th>Hesabda qalıq</th>
                                    <th>Əməliyyat</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="t" items="${list}" varStatus="loop">
                                    <tr>
                                        <td>${loop.index + 1}</td>
                                        <td><c:out value="${t.id}" /></td>
                                        <td><c:out value="${t.account.accountNumber}" /></td>
                                        <td><c:out value="${t.description}" /></td>
                                        <td><fmt:formatDate value = "${t.createdDate}" pattern = "dd.MM.yyyy" /></td>
                                        <td><c:out value="${t.amount}" /> ədəd</td>
                                        <td>
                                            <span><c:out value="${t.price}" /></span>
                                            <span class="kt-font-bold font-italic font-size-10px"><c:out value="${t.currency}" /></span>
                                        </td>
                                        <td><c:out value="${t.rate}" /></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${t.debt and t.sumPrice!=0}">
                                                    <span class="kt-font-bold kt-font-success"><c:out value="${t.sumPrice}" /></span>
                                                    <span class="kt-font-bold kt-font-success font-italic font-size-10px">AZN</span>
                                                </c:when>
                                                <c:when test="${!t.debt and t.sumPrice!=0}">
                                                    <span class="kt-font-bold kt-font-danger">-<c:out value="${t.sumPrice}" /></span>
                                                    <span class="kt-font-bold kt-font-danger font-italic font-size-10px">AZN</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="kt-font-bold"><c:out value="${t.sumPrice}" /></span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td><c:out value="${t.balance}" /></td>
                                        <td nowrap class="text-center">
                                            <c:set var="approve" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'approve')}"/>
                                            <c:choose>
                                                <c:when test="${approve.status}">
                                                    <c:if test="${!t.approve}">
                                                        <a href="javascript:approve($('#transaction-approve-form'), $('#transaction-approve-modal'), '<c:out value="${t.id}" />', '<c:out value="${t.description}" />', '<c:out value="${t.price}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${approve.object.name}"/>">
                                                            <i class="<c:out value="${approve.object.icon}"/>"></i>
                                                        </a>
                                                    </c:if>
                                                </c:when>
                                            </c:choose>
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
                                                    <a href="javascript:deleteData('<c:out value="${t.id}" />', '<c:out value="${t.description}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${delete.object.name}"/>">
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
                <h5 class="modal-title"></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="form" id="form" method="post" action="/accounting/transaction" cssClass="form-group">
                    <form:hidden path="id"/>
                    <div class="form-group">
                        <form:label path="description">Açıqlama</form:label>
                        <form:input path="description" cssClass="form-control"/>
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
<div class="modal fade" id="transaction-approve-modal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title"></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="form" id="transaction-approve-form" method="post" action="/accounting/transaction-approve" cssClass="form-group">
                    <form:hidden path="id"/>
                    <div class="form-group">
                        <form:label path="account">Hesab</form:label>
                        <form:select  path="account" cssClass="custom-select form-control">
                            <form:options items="${accounts}" itemLabel="accountNumber" itemValue="id" />
                        </form:select>
                        <form:errors path="account" cssClass="alert-danger control-label"/>
                    </div>
                    <div class="form-group">
                        <form:label path="description">Açıqlama</form:label>
                        <form:textarea path="description" cssClass="form-control"/>
                        <form:errors path="description" cssClass="alert-danger control-label"/>
                    </div>
                    <div class="row">
                        <div class="col-sm-8">
                            <div class="form-group">
                                <form:label path="price">Alış qiyməti</form:label>
                                <div class="input-group" >
                                    <form:input path="price" cssClass="form-control" placeholder="Qiyməti daxil edin"/>
                                    <div class="input-group-append">
                                                    <span class="input-group-text">
                                                        <i class="la la-usd"></i>
                                                    </span>
                                    </div>
                                </div>
                                <form:errors path="price" cssClass="alert-danger control-label"/>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="form-group">
                                <form:label path="currency">&nbsp;</form:label>
                                <form:select  path="currency" cssClass="custom-select form-control">
                                    <form:options items="${currencies}" itemLabel="name" itemValue="name" />
                                </form:select>
                            </div>
                        </div>
                    </div>
                </form:form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="submit($('#transaction-approve-form'));">Bəli, təsdiq edirəm!</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Bağla</button>
            </div>
        </div>
    </div>
</div>


<script>
    function approve(form, modal, id, description, price){
        $(form).find("#id").val(id);
        $(form).find("#price").val(price);
        $(form).find("#description").val(description);

        $(modal).find(".modal-title").html('Təsdiq et!');
        $(modal).modal('toggle');
    }

</script>