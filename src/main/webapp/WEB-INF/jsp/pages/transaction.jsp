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
    <c:set var="delete" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'delete')}"/>
    <c:set var="filter" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'filter')}"/>
    <c:if test="${filter.status}">
        <div class="accordion  accordion-toggle-arrow mb-2" id="accordionFilter">
            <div class="card" style="border-radius: 4px;">
                <div class="card-header">
                    <div class="card-title w-100" data-toggle="collapse" data-target="#filterContent" aria-expanded="true" aria-controls="collapseOne4">
                        <div class="row w-100">
                            <div class="col-3">
                                <i class="<c:out value="${filter.object.icon}"/>"></i>
                                <c:out value="${list.totalElements>0?list.totalElements:0} sətr"/>
                            </div>
                            <div class="col-6 text-center" style="letter-spacing: 10px;">
                                <c:out value="${filter.object.name}"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="filterContent" class="collapse show" aria-labelledby="headingOne" data-parent="#accordionFilter">
                    <div class="card-body">
                        <form:form modelAttribute="filter" id="filter" method="post" action="/accounting/transaction/filter">
                            <form:hidden path="organization" />
                            <div class="row">
                                <div class="col-md-11">
                                    <div class="row">
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="id">KOD</form:label>
                                                <form:input path="id" cssClass="form-control" placeholder="######"/>
                                                <form:errors path="id" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="description">Açıqlama</form:label>
                                                <form:input path="description" cssClass="form-control" placeholder="Açıqlamanı daxil edin" />
                                                <form:errors path="description" cssClass="alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="action">Xərc</form:label>
                                                <form:select path="action.id" cssClass="custom-select form-control">
                                                    <form:option value=""></form:option>
                                                    <form:options items="${actions}" itemLabel="name" itemValue="id"/>
                                                </form:select>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="currency">Valyuta</form:label>
                                                <form:input path="currency" cssClass="form-control" placeholder="AZN, EUR, USD, GBP"/>
                                                <form:errors path="currency" cssClass="alert-danger control-label"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="priceFrom">Qiymətdən</form:label>
                                                <form:input path="priceFrom" cssClass="form-control" placeholder="Qiyməti daxil edin"/>
                                                <form:errors path="priceFrom" cssClass="alert-danger control-label"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="price">Qiymətədək</form:label>
                                                <form:input path="price" cssClass="form-control" placeholder="Qiyməti daxil edin"/>
                                                <form:errors path="price" cssClass="alert-danger control-label"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="transactionDateFrom">Tarixdən</form:label>
                                                <div class="input-group date">
                                                    <form:input path="transactionDateFrom" autocomplete="off"
                                                                cssClass="form-control datetimepicker-element" date="datetime"
                                                                placeholder="dd.MM.yyyy HH:mm"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="transactionDateFrom" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="transactionDate">Tarixədək</form:label>
                                                <div class="input-group date">
                                                    <form:input path="transactionDate" autocomplete="off"
                                                                cssClass="form-control datetimepicker-element" date="datetime"
                                                                placeholder="dd.MM.yyyy HH:mm"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="transactionDate" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2" style="padding-top: 30px;">
                                            <div class="form-group">
                                                <label class="kt-checkbox kt-checkbox--brand">
                                                    <form:checkbox path="approve"/> Təsdiq edilənlər
                                                    <span></span>
                                                </label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-1 text-right">
                                    <div class="form-group">
                                        <a href="#" onclick="location.reload();" class="btn btn-danger btn-elevate btn-icon-sm btn-block mb-2" style="padding: 0.35rem 0.6rem;">
                                            <i class="la la-trash"></i> Sil
                                        </a>
                                        <a href="#" onclick="submit($('#filter'))" class="btn btn-warning btn-elevate btn-icon-sm btn-block mt-2" style="padding: 0.35rem 0.6rem">
                                            <i class="la la-search"></i> Axtar
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
    <div class="row">
        <div class="col-lg-12">
            <div class="kt-portlet kt-portlet--mobile">
                <div class="kt-portlet__body">
                    <c:choose>
                        <c:when test="${not empty list}">
                            <c:set var="approve" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'approve')}"/>
                            <c:set var="view" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'view')}"/>
                            <c:set var="edit" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'edit')}"/>
                            <table class="table table-striped- table-bordered table-hover table-checkable" id="datatable">
                                <thead>
                                <tr>
                                    <th>№</th>
                                    <th>ID</th>
                                    <th>Nədir?</th>
                                    <th>Flial</th>
                                    <th>Hesab nömrəsi</th>
                                    <th>Açıqlama</th>
                                    <th>Tarix/Vaxt</th>
                                    <th>Miqdar</th>
                                    <th>Qiymət</th>
                                    <th>Kurs</th>
                                    <th>Ümumi qiymət</th>
                                    <th>Hesabda qalıq</th>
                                    <th>Əməliyyat</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="t" items="${list.content}" varStatus="loop">
                                    <tr data="<c:out value="${utl:toJson(t)}" />">
                                        <td>${loop.index + 1}</td>
                                        <td><c:out value="${t.id}" /></td>
                                        <td><c:out value="${t.action.name}" /></td>
                                        <td><div style="width: 90px;"><c:out value="${t.organization.name}" /></div></td>
                                        <td><c:out value="${t.account.accountNumber}" /></td>
                                        <td><c:out value="${t.description}" /></td>
                                        <td><fmt:formatDate value = "${t.transactionDate}" pattern = "dd.MM.yyyy HH:mm:ss" /></td>
                                        <td><c:out value="${t.amount}" /> ədəd</td>
                                        <td>
                                            <div style="width: 65px;">
                                                <span><c:out value="${t.price}" /></span>
                                                <span class="kt-font-bold font-italic font-size-10px"><c:out value="${t.currency}" /></span>
                                            </div>
                                        </td>
                                        <td><c:out value="${t.rate}" /></td>
                                        <td>
                                            <div style="width: 75px;">
                                            <c:choose>
                                                <c:when test="${t.debt and t.sumPrice!=0 and t.approve}">
                                                    <span class="kt-font-bold kt-font-success"><c:out value="${t.sumPrice}" /></span>
                                                    <span class="kt-font-bold kt-font-success font-italic font-size-10px">AZN</span>
                                                    <i class="la la-arrow-down kt-font-bold kt-font-success"></i>
                                                </c:when>
                                                <c:when test="${!t.debt and t.sumPrice!=0 and t.approve}">
                                                    <span class="kt-font-bold kt-font-danger">-<c:out value="${t.sumPrice}" /></span>
                                                    <span class="kt-font-bold kt-font-danger font-italic font-size-10px">AZN</span>
                                                    <i class="la la-arrow-up kt-font-bold kt-font-danger" style="font-weight: bold;"></i>
                                                </c:when>
                                                <c:when test="${t.debt and t.sumPrice!=0 and !t.approve}">
                                                    <span class="kt-font-bold"><c:out value="${t.sumPrice}" /></span>
                                                    <span class="kt-font-bold font-italic font-size-10px">AZN</span>
                                                </c:when>
                                                <c:when test="${!t.debt and t.sumPrice!=0 and !t.approve}">
                                                    <span class="kt-font-bold">-<c:out value="${t.sumPrice}" /></span>
                                                    <span class="kt-font-bold font-italic font-size-10px">AZN</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="kt-font-bold"><c:out value="${t.sumPrice}" /></span>
                                                </c:otherwise>
                                            </c:choose>
                                            </div>
                                        </td>
                                        <td><c:out value="${t.balance}" /></td>
                                        <td nowrap class="text-center">
                                            <c:if test="${approve.status}">
                                                <c:if test="${!t.approve}">
                                                    <a href="javascript:approve($('#transaction-approve-form'), $('#transaction-approve-modal'), '<c:out value="${t.id}" />', '<c:out value="${t.description}" />', '<c:out value="${t.price}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${approve.object.name}"/>">
                                                        <i class="<c:out value="${approve.object.icon}"/>"></i>
                                                    </a>
                                                </c:if>
                                            </c:if>
                                            <c:if test="${view.status}">
                                                <a href="#" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${view.object.name}"/>">
                                                    <i class="la <c:out value="${view.object.icon}"/>"></i>
                                                </a>
                                            </c:if>
                                            <c:if test="${edit.status}">
                                                <a href="javascript:edit($('#form'), '<c:out value="${utl:toJson(t)}" />', 'modal-operation', '<c:out value="${edit.object.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${edit.object.name}"/>">
                                                    <i class="<c:out value="${edit.object.icon}"/>"></i>
                                                </a>
                                            </c:if>
                                            <c:if test="${delete.status}">
                                                <a href="javascript:deleteData('<c:out value="${t.id}" />', '<c:out value="${t.description}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${delete.object.name}"/>">
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
                    <form:hidden path="organization" />
                    <form:hidden path="inventory" />
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <form:label path="action">Xərc</form:label>
                                <form:select path="action" cssClass="custom-select form-control">
                                    <form:option value=""></form:option>
                                    <form:options items="${actions}" itemLabel="name" itemValue="id"/>
                                </form:select>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <form:label path="account">Hesab</form:label>
                                <form:select  path="account" cssClass="custom-select form-control">
                                    <form:option value=""></form:option>
                                    <form:options items="${accounts}" itemLabel="accountNumberWithCurrency" itemValue="id" />
                                </form:select>
                                <form:errors path="account" cssClass="alert-danger control-label"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <form:label path="price">Məbləğ</form:label>
                                <div class="input-group" >
                                    <div class="input-group-prepend"><span class="input-group-text"><i class="la la-usd"></i></span></div>
                                    <form:input path="price" cssClass="form-control" placeholder="Balansı daxil edin"/>
                                </div>
                                <form:errors path="price" cssClass="alert-danger control-label"/>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <form:label path="amount">Say</form:label>
                                <div class="input-group" >
                                    <div class="input-group-prepend"><span class="input-group-text"><i class="la la-calculator"></i></span></div>
                                    <form:input path="amount" cssClass="form-control" placeholder="Say daxil edin"/>
                                </div>
                                <form:errors path="amount" cssClass="alert-danger control-label"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <form:label path="transactionDate">Tarix</form:label>
                                <div class="input-group date" >
                                    <div class="input-group-prepend"><span class="input-group-text"><i class="la la-calendar"></i></span></div>
                                    <form:input path="transactionDate" cssClass="form-control datetimepicker-element" date="datetime" placeholder="dd.MM.yyyy HH:mm"/>
                                </div>
                                <form:errors path="transactionDate" cssClass="control-label alert-danger" />
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group" style="padding-top: 30px;">
                                <label class="kt-checkbox kt-checkbox--brand">
                                    <form:checkbox path="debt"/> Debt
                                    <span></span>
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <form:label path="description">Açıqlama</form:label>
                        <form:textarea path="description" cssClass="form-control" />
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
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title"></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="form" id="transaction-approve-form" method="post" action="/accounting/transaction/approve" cssClass="form-group">
                    <form:hidden path="id"/>
                    <div class="form-group">
                        <form:label path="account">Hesab</form:label>
                        <form:select  path="account" cssClass="custom-select form-control">
                            <form:option value=""></form:option>
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
                                <form:label path="price">Qiyməti</form:label>
                                <div class="input-group" >
                                    <div class="input-group-prepend"><span class="input-group-text"><i class="la la-usd"></i></span></div>
                                    <form:input path="price" cssClass="form-control" placeholder="Qiyməti daxil edin"/>
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
                                <form:errors path="currency" cssClass="alert-danger control-label"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label>Digər xərclər varmı?</label>
                        <div class="row mt-1">
                            <div class="col-md-12">
                                <c:forEach var="t" items="${expenses}" varStatus="loop">
                                    <label class="kt-checkbox kt-checkbox--brand">
                                        <input type="checkbox" name="expense" value="${t.id}"/><c:out value="${t.name}"/>
                                        <span></span>
                                    </label>
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                </c:forEach>
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

<script>
<c:if test="${edit.status}">
    $('#datatable tbody').on('dblclick', 'tr', function () {
    edit($('#form'), $(this).attr('data'), 'modal-operation', 'Redaktə');
    });
</c:if>

$("#datatable").DataTable({
    responsive: true,
    lengthMenu: [10, 25, 50, 75, 100, 200, 1000],
    pageLength: 100,
    order: [[1, 'desc']],
    columnDefs: [
        {
            targets: 0,
            width: '25px',
            className: 'dt-center',
            orderable: false
        },
    ],
});

$( "#form" ).validate({
    rules: {
        amount: {
            required: false,
            digits: true,
            min: 1
        },
        price: {
            required: true,
            number: true
        },
        account: {
            required: true
        }
    },
    invalidHandler: function(event, validator) {
        swal.close();
    },
})
$( "#transaction-approve-form" ).validate({
    rules: {
        price: {
            required: true,
            number: true
        },
        account: {
            required: true
        },
        currency: {
            required: true
        }
    },
    invalidHandler: function(event, validator) {
        swal.close();
    },
})

$("input[name='amount']").inputmask('decimal', {
    rightAlignNumerics: false
});

$("input[name='price']").inputmask('decimal', {
    rightAlignNumerics: false
});

</script>
