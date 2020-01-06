<%@ page import="java.util.Date" %>
<%@ page import="com.openerp.util.DateUtility" %><%--
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
        <c:set var="payroll" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'payroll')}"/>
        <c:set var="sale" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'sale')}"/>
        <c:set var="edit" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'edit')}"/>
        <c:set var="delete" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'delete')}"/>
<table class="table table-striped- table-bordered table-hover table-checkable" id="group_table">
    <thead>
    <tr>
        <th>№</th>
        <th>ID</th>
        <th>Struktur</th>
        <th>Ad Soyad Ata adı</th>
        <th>Vəzifə</th>
        <th>İşə başlıyıb</th>
        <th>İşdən ayrılıb</th>
        <th>Aktiv</th>
        <th>Əməliyyat</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="t" items="${list}" varStatus="loop">
        <tr data="<c:out value="${utl:toJson(t)}" />">
            <td>${loop.index + 1}</td>
            <td><c:out value="${t.id}" /></td>
            <td><c:out value="${t.organization.name}" /></td>
            <th><c:out value="${t.person.firstName}"/> <c:out value="${t.person.lastName}"/> <c:out value="${t.person.fatherName}"/></th>
            <td><c:out value="${t.position.name}" /></td>
            <td><fmt:formatDate value = "${t.contractStartDate}" pattern = "dd.MM.yyyy" /></td>
            <td><fmt:formatDate value = "${t.contractEndDate}" pattern = "dd.MM.yyyy" /></td>
            <c:choose>
                <c:when test="${empty t.contractEndDate}">
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
                <span class="dropdown">
                    <a href="#" class="btn btn-sm btn-clean btn-icon btn-icon-md" data-toggle="dropdown" aria-expanded="true">
                      <i class="la la-ellipsis-h"></i>
                    </a>
                    <div class="dropdown-menu dropdown-menu-right">
                        <c:if test="${payroll.status}">
                            <a href="javascript:edit($('#form-payroll'), '<c:out value="${utl:toJson(t)}" />', 'modal-payroll', '<c:out value="${payroll.object.name}" />');" class="dropdown-item" title="<c:out value="${payroll.object.name}"/>">
                                <i class="<c:out value="${payroll.object.icon}"/>"></i> <c:out value="${payroll.object.name}"/>
                            </a>
                        </c:if>
                        <c:if test="${sale.status}">
                            <a href="javascript:edit($('#form-sale'), '<c:out value="${utl:toJson(t)}" />', 'modal-sale', '<c:out value="${sale.object.name}" />');" class="dropdown-item" title="<c:out value="${sale.object.name}"/>">
                                <i class="<c:out value="${sale.object.icon}"/>"></i> <c:out value="${sale.object.name}"/>
                            </a>
                        </c:if>
                        <c:if test="${delete.status}">
                            <a href="javascript:deleteData('<c:out value="${t.id}" />', '<c:out value="${t.person.firstName}"/> <c:out value="${t.person.lastName}"/> <c:out value="${t.person.fatherName}"/>');" class="dropdown-item" title="<c:out value="${delete.object.name}"/>">
                                <i class="<c:out value="${delete.object.icon}"/>"></i> <c:out value="${delete.object.name}"/>
                            </a>
                        </c:if>
                    </div>
                </span>
                <c:if test="${edit.status}">
                    <a href="javascript:edit($('#form'), '<c:out value="${utl:toJson(t)}" />', 'modal-operation', '<c:out value="${edit.object.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${edit.object.name}"/>">
                        <i class="<c:out value="${edit.object.icon}"/>"></i>
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
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title"></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="form" id="form" method="post" action="/hr/employee" cssClass="form-group">
                    <form:input path="id" type="hidden"/>
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <form:label path="person.firstName">Ad</form:label>
                                <form:input path="person.firstName" cssClass="form-control" placeholder="Adı daxil edin Məs: Səbuhi"/>
                                <form:errors path="person.firstName" cssClass="control-label alert-danger" />
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <form:label path="person.lastName">Soyad</form:label>
                                <form:input path="person.lastName" cssClass="form-control" placeholder="Soyadı daxil edin Məs: Vəliyev"/>
                                <form:errors path="person.lastName" cssClass="control-label alert-danger" />
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <form:label path="person.fatherName">Ata adı</form:label>
                                <form:input path="person.fatherName" cssClass="form-control" placeholder="Ata adını daxil edin"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <form:label path="person.birthday">Doğum tarixi</form:label>
                                <div class="input-group date" >
                                    <form:input path="person.birthday" autocomplete="off" cssClass="form-control datepicker-element" date="date" placeholder="dd.MM.yyyy"/>
                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                                    </div>
                                </div>
                                <form:errors path="person.birthday" cssClass="control-label alert-danger" />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <form:label path="person.gender" cssClass="mb-3">Cins</form:label><br/>
                                <c:forEach var="t" items="${genders}" varStatus="loop">
                                    <label class="kt-radio kt-radio--brand">
                                        <form:radiobutton path="person.gender" value="${t.id}"/> <c:out value="${t.name}"/>
                                        <span></span>
                                    </label>
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                </c:forEach>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <form:label path="person.maritalStatus">Ailə vəziyyəti</form:label>
                                <form:select  path="person.maritalStatus" cssClass="custom-select form-control">
                                    <form:options items="${marital_statuses}" itemLabel="name" itemValue="id" />
                                </form:select>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <form:label path="person.nationality">Milliyət</form:label>
                                <form:select  path="person.nationality" cssClass="custom-select form-control">
                                    <form:options items="${nationalities}" itemLabel="name" itemValue="id" />
                                </form:select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <form:label path="person.idCardSerialNumber">Ş.v - nin seriya nömrəsi</form:label>
                                <form:input path="person.idCardSerialNumber" cssClass="form-control" placeholder="AA0822304"/>
                                <form:errors path="person.idCardSerialNumber" cssClass="control-label alert-danger" />
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <form:label path="person.idCardPinCode">Ş.v - nin pin kodu</form:label>
                                <form:input path="person.idCardPinCode" cssClass="form-control" placeholder="Məs: 4HWL0AM"/>
                                <form:errors path="person.idCardPinCode" cssClass="control-label alert-danger" />
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <form:label path="person.voen">VÖEN</form:label>
                                <form:input path="person.voen" cssClass="form-control" placeholder="Məs: 0000000000"/>
                                <form:errors path="person.voen" cssClass="control-label alert-danger" />
                            </div>
                        </div>
                    </div>
                    <hr style="width: 100%"/>
                    <div class="row">
                        <div class="col-md-7">
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <form:label path="organization">Struktur</form:label>
                                        <form:select  path="organization" cssClass="custom-select form-control">
                                            <form:options items="${organizations}" itemLabel="name" itemValue="id" />
                                        </form:select>
                                        <form:errors path="organization" cssClass="control-label alert-danger" />
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <form:label path="position">Vəzifə</form:label>
                                        <form:select  path="position" cssClass="custom-select form-control">
                                            <form:options items="${positions}" itemLabel="name" itemValue="id" />
                                        </form:select>
                                        <form:errors path="position" cssClass="control-label alert-danger" />
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <form:label path="contractStartDate">İşə başlama tarixi</form:label>
                                        <div class="input-group date" >
                                            <form:input path="contractStartDate" autocomplete="off" date="date" cssClass="form-control datepicker-element" placeholder="dd.MM.yyyy"/>
                                            <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                                            </div>
                                        </div>
                                        <form:errors path="contractStartDate" cssClass="control-label alert-danger" />
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <form:label path="socialCardNumber">Sosial kart nömrəsi</form:label>
                                        <form:input path="socialCardNumber" cssClass="form-control" placeholder="Sosial kart nömrəsini daxil edin"/>
                                        <form:errors path="socialCardNumber" cssClass="control-label alert-danger" />
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <form:label path="bankAccountNumber">Bank hesab nömrəsi</form:label>
                                        <form:input path="bankAccountNumber" cssClass="form-control" placeholder="Bank hesab nömrəsini daxil edin"/>
                                        <form:errors path="bankAccountNumber" cssClass="control-label alert-danger" />
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <form:label path="bankCardNumber">Bank kart nömrəsi</form:label>
                                        <form:input path="bankCardNumber" cssClass="form-control" placeholder="Bank kart nömrəsini daxil edin"/>
                                        <form:errors path="bankCardNumber" cssClass="control-label alert-danger" />
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-5 bg-light">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <form:label path="employeeRestDays">İstirahət günləri</form:label>
                                        <form:select  path="employeeRestDays" cssClass="custom-select form-control" multiple="multiple">
                                            <form:options items="${week_days}" itemLabel="name" itemValue="id" />
                                        </form:select>
                                        <form:errors path="employeeRestDays" cssClass="control-label alert-danger" />
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-5">
                                    <div class="form-group">
                                        <label class="kt-checkbox kt-checkbox--brand">
                                            <form:checkbox path="person.disability"/> Əlillik varmı?
                                            <span></span>
                                        </label>
                                    </div>
                                </div>
                                <div class="col-md-7">
                                    <div class="form-group">
                                        <label class="kt-checkbox kt-checkbox--brand">
                                            <form:checkbox path="specialistOrManager"/> Mütəxəsis və ya rəhbərdirmi?
                                            <span></span>
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="row" style="margin-top: -4px;">
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <form:label path="description">Açıqlama</form:label>
                                        <form:textarea path="description" cssClass="form-control"/>
                                        <form:errors path="description" cssClass="control-label alert-danger" />
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <hr style="width: 100%"/>
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <form:label path="person.contact.email">Email</form:label>
                                <div class="input-group" >
                                    <form:input path="person.contact.email" cssClass="form-control" placeholder="example@example.com"/>
                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-at"></i>
                                        </span>
                                    </div>
                                </div>
                                <form:errors path="person.contact.email" cssClass="control-label alert-danger" />
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <form:label path="person.contact.mobilePhone">Mobil nömrə</form:label>
                                <div class="input-group" >
                                    <form:input path="person.contact.mobilePhone" cssClass="form-control" placeholder="505505550"/>
                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-phone"></i>
                                        </span>
                                    </div>
                                </div>
                                <form:errors path="person.contact.mobilePhone" cssClass="control-label alert-danger" />
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <form:label path="person.contact.homePhone">Şəhər nömrəsi</form:label>
                                <div class="input-group" >
                                    <form:input path="person.contact.homePhone" cssClass="form-control" placeholder="124555050"/>
                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-phone"></i>
                                        </span>
                                    </div>
                                </div>
                                <form:errors path="person.contact.homePhone" cssClass="control-label alert-danger" />
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <form:label path="person.contact.city">Şəhər</form:label>
                                <form:select  path="person.contact.city" cssClass="custom-select form-control">
                                    <form:options items="${cities}" itemLabel="name" itemValue="id" />
                                </form:select>
                            </div>
                        </div>
                        <div class="col-md-8">
                            <div class="form-group">
                                <form:label path="person.contact.address">Ünvan</form:label>
                                <div class="input-group" >
                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-street-view"></i>
                                        </span>
                                    </div>
                                    <form:input path="person.contact.address" cssClass="form-control" placeholder="Küçə adı, ev nömrəsi və s."/>
                                </div>
                                <form:errors path="person.contact.address" cssClass="control-label alert-danger" />
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

<div class="modal fade" id="modal-payroll" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Əmək haqqı məlumatları</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="form" id="form-payroll" method="post" action="/hr/employee/payroll" cssClass="form-group">
                    <input type="hidden" name="id"/>
                    <div class="row mb-4">
                        <div class="col-md-6 text-right">
                            <input type="text" name="person.firstName" class="style-none" disabled style="text-align: right;">
                        </div>
                        <div class="col-md-6">
                            <input type="text" name="person.lastName" class="style-none" disabled>
                        </div>
                    </div>
                    <c:forEach var="t" items="${employee_payroll_fields}" varStatus="loop">
                        <div class="form-group-0_5">
                            <div class="row">
                                <div class="col-md-8 text-right" style="padding-top: 8px;">
                                    <label><c:out value="${t.name}"/></label>
                                </div>
                                <div class="col-md-4">
                                    <input type="hidden" name="employeePayrollDetails[${loop.index}].employeePayrollField" value="${t.id}"/>
                                    <input type="hidden" name="employeePayrollDetails[${loop.index}].key" value="${t.attr1}"/>
                                    <c:choose>
                                        <c:when test="${t.attr1 eq '{previous_work_experience}'}">
                                            <input type="text" name="employeePayrollDetails[${loop.index}].value" value="${t.attr2}" key="${t.attr1}" onkeyup="calculateVacationDay($('input[name=\'person.disability\']'), $('input[name=specialistOrManager]'), $('input[name=contractStartDate]'), $('input[key=\'{previous_work_experience}\']'))" class="form-control" />
                                        </c:when>
                                        <c:otherwise>
                                            <input type="text" name="employeePayrollDetails[${loop.index}].value" value="${t.attr2}" key="${t.attr1}" class="form-control" />
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </form:form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="submit($('#form-payroll'));">Yadda saxla</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Bağla</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="modal-sale" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Satış məlumatları</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="form" id="form-sale" method="post" action="/hr/employee/sale" cssClass="form-group">
                    <input type="hidden" name="id"/>
                    <div class="row mb-4">
                        <div class="col-md-6 text-right">
                            <input type="text" name="person.firstName" class="style-none" disabled style="text-align: right;">
                        </div>
                        <div class="col-md-6">
                            <input type="text" name="person.lastName" class="style-none" disabled>
                        </div>
                    </div>
                    <c:forEach var="t" items="${employee_sale_fields}" varStatus="loop">
                        <div class="form-group-0_5">
                            <div class="row">
                                <div class="col-md-3 text-right" style="padding-top: 8px;">
                                    <label><c:out value="${t.name}"/></label>
                                </div>
                                <div class="col-md-9">
                                    <input type="hidden" name="employeeSaleDetails[${loop.index}].employeeSalaryField" value="${t.id}"/>
                                    <input type="hidden" name="employeeSaleDetails[${loop.index}].key" value="${t.attr1}"/>
                                    <input type="text" name="employeeSaleDetails[${loop.index}].value" value="${t.attr2}" class="form-control" />
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </form:form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="submit($('#form-sale'));">Yadda saxla</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Bağla</button>
            </div>
        </div>
    </div>
</div>

<script>
    $('#employeeRestDays').select2({
        placeholder: "Həftə günlərini seçin",
        allowClear: true
    });


    var KTDatatablesAdvancedRowGrouping = function() {

        var initTable1 = function() {
            var table = $('#group_table');

            table.DataTable({
                responsive: true,
                pageLength: 100,
                order: [[2, 'asc']],
                drawCallback: function(settings) {
                    var api = this.api();
                    var rows = api.rows({page: 'current'}).nodes();
                    var last = null;

                    api.column(2, {page: 'current'}).data().each(function(group, i) {
                        if (last !== group) {
                            $(rows).eq(i).before(
                                '<tr class="group"><td colspan="30">' + group + '</td></tr>'
                            );
                            last = group;
                        }
                    });
                },
                columnDefs: [
                    {
                        targets: [2],
                        visible: false
                    }
                ]/*,
                columns: [
                    {data: 'id'},
                    {data: 'organization.name'},
                    {data: 'organization.name'},
                    {data: 'organization.name'},
                    {data: 'organization.name'},
                    {data: 'organization.name'},
                    {data: 'organization.name'},
                    {data: 'organization.name'},
                    {data: 'organization.name'}
                ]*/
            });
        };

        return {
            init: function() {
                initTable1();
            }
        };
    }();

    jQuery(document).ready(function() {
        KTDatatablesAdvancedRowGrouping.init();
    });
    
    function calculateVacationDay(disability, specialistOrManager, contractStartDate, previousWorkExperience) {
        $("input[key='{main_vacation_days}']").val("21");
        if($(disability).is(":checked")){
            $("input[key='{main_vacation_days}']").val("43");
        }

        if($(specialistOrManager).is(":checked") && !$(disability).is(":checked")){
            $("input[key='{main_vacation_days}']").val("30");
        }
        $("input[key='{additional_vacation_days}']").val("0");
        var current = calculateCurrentWorkExperience($(contractStartDate).val(), "<%= DateUtility.getFormattedDate(new Date())%>");
        var experience = parseFloat(current)+parseFloat($(previousWorkExperience).val());
        if(experience>=15){
            $("input[key='{additional_vacation_days}']").val("6");
        } else if(experience>=10){
            $("input[key='{additional_vacation_days}']").val("4");
        } else if(experience>=5){
            $("input[key='{additional_vacation_days}']").val("2");
        }
        if($(disability).is(":checked")){
            $("input[key='{additional_vacation_days}']").val("0");
        }
    }

    function calculateCurrentWorkExperience(contractStartDate, today){
        var array1 = contractStartDate.split(".");
        var array2 = today.split(".");
        return parseFloat(array2[2])-parseFloat(array1[2])
    }
    <c:if test="${edit.status}">
        $('#group_table tbody').on('dblclick', 'tr', function () {
            edit($('#form'), $(this).attr('data'), 'modal-operation', 'Redaktə');
        });
    </c:if>
</script>

