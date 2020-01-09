<%--
  Created by IntelliJ IDEA.
  User: irkan.ahmadov
  Date: 01.09.2019
  Time: 1:22
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="utl" uri="/WEB-INF/tld/Util.tld" %>
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
                        <form:form modelAttribute="filter" id="filter" method="post" action="/crm/customer/filter">
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
                                                <form:label path="person.firstName">Ad</form:label>
                                                <form:input path="person.firstName" cssClass="form-control"
                                                            placeholder="Adı daxil edin Məs: Səbuhi"/>
                                                <form:errors path="person.firstName" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="person.lastName">Soyad</form:label>
                                                <form:input path="person.lastName" cssClass="form-control"
                                                            placeholder="Soyadı daxil edin Məs: Vəliyev"/>
                                                <form:errors path="person.lastName" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="person.birthday">Doğum tarixi</form:label>
                                                <div class="input-group date">
                                                    <form:input path="person.birthday" autocomplete="off"
                                                                cssClass="form-control datepicker-element" date="date"
                                                                placeholder="dd.MM.yyyy"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="person.birthday" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="person.idCardSerialNumber">Ş.v - nin seriya nömrəsi</form:label>
                                                <form:input path="person.idCardSerialNumber" cssClass="form-control" placeholder="AA0822304"/>
                                                <form:errors path="person.idCardSerialNumber" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="person.idCardPinCode">Ş.v - nin pin kodu</form:label>
                                                <form:input path="person.idCardPinCode" cssClass="form-control" placeholder="Məs: 4HWL0AM"/>
                                                <form:errors path="person.idCardPinCode" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="person.contact.email">Email</form:label>
                                                <div class="input-group">
                                                    <form:input path="person.contact.email" cssClass="form-control"
                                                                placeholder="example@example.com"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-at"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="person.contact.email" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="person.contact.mobilePhone">Mobil nömrə</form:label>
                                                <div class="input-group">
                                                    <form:input path="person.contact.mobilePhone" cssClass="form-control"
                                                                placeholder="505505550"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-phone"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="person.contact.mobilePhone" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="person.contact.homePhone">Şəhər nömrəsi</form:label>
                                                <div class="input-group">
                                                    <form:input path="person.contact.homePhone" cssClass="form-control"
                                                                placeholder="124555050"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-phone"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="person.contact.homePhone" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="person.contact.city">Şəhər</form:label>
                                                <form:select path="person.contact.city.id" cssClass="custom-select form-control">
                                                    <form:option value=""></form:option>
                                                    <form:options items="${cities}" itemLabel="name" itemValue="id"/>
                                                </form:select>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="person.contact.address">Ünvan</form:label>
                                                <div class="input-group">
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-street-view"></i>
                                        </span>
                                                    </div>
                                                    <form:input path="person.contact.address" cssClass="form-control"
                                                                placeholder="Küçə adı, ev nömrəsi və s."/>
                                                </div>
                                                <form:errors path="person.contact.address" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <c:if test="${delete.status}">
                                            <div class="col-md-2" style="padding-top: 30px;">
                                                <div class="form-group">
                                                    <label class="kt-checkbox kt-checkbox--brand">
                                                        <form:checkbox path="active"/> Aktual məlumat
                                                        <span></span>
                                                    </label>
                                                </div>
                                            </div>
                                        </c:if>
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
                <span class="kt-portlet__body">
                    <c:choose>
                        <c:when test="${not empty list}">
                            <c:set var="edit" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'edit')}"/>
                            <table class="table table-striped- table-bordered table-hover table-checkable"
                                   id="group_table">
                                <thead>
                                <tr>
                                    <th>KOD</th>
                                    <th>Struktur</th>
                                    <th>Ad Soyad Ata adı</th>
                                    <th>Şəhər</th>
                                    <th>Doğum tarixi</th>
                                    <th>Ş.v - nin seriya nömrəsi</th>
                                    <th>Ş.v - nin pin kodu</th>
                                    <th>Əməliyyat</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="t" items="${list.content}" varStatus="loop">
                                    <tr data="<c:out value="${utl:toJson(t)}" />">
                                        <td><c:out value="${t.id}"/></td>
                                        <th>
                                            <c:out value="${t.person.firstName}"/> <c:out value="${t.person.lastName}"/> <c:out
                                                value="${t.person.fatherName}"/>
                                        </th>
                                        <td><c:out value="${t.organization.name}"/></td>
                                        <th>
                                            <c:out value="${t.person.contact.city.name}"/>
                                        </th>
                                        <td><fmt:formatDate value="${t.person.birthday}" pattern="dd.MM.yyyy"/></td>
                                        <td><c:out value="${t.person.idCardSerialNumber}"/></td>
                                        <td><c:out value="${t.person.idCardPinCode}"/></td>
                                        <td nowrap class="text-center">
                                            <c:if test="${edit.status}">
                                                <a href="javascript:edit($('#form'), '<c:out value="${utl:toJson(t)}" />', 'modal-operation', '<c:out value="${edit.object.name}" />');"
                                                   class="btn btn-sm btn-clean btn-icon btn-icon-md"
                                                   title="<c:out value="${edit.object.name}"/>">
                                                    <i class="<c:out value="${edit.object.icon}"/>"></i>
                                                </a>
                                            </c:if>
                                            <c:if test="${delete.status}">
                                                <a href="javascript:deleteData('<c:out value="${t.id}" />', '<c:out value="${t.person.firstName}" /> <c:out value="${t.person.lastName}" /> <c:out value="${t.person.fatherName}" />');"
                                                   class="btn btn-sm btn-clean btn-icon btn-icon-md"
                                                   title="<c:out value="${delete.object.name}"/>">
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
                <form:form modelAttribute="form" id="form" method="post" action="/crm/customer" cssClass="form-group">
                    <form:input path="id" type="hidden"/>
                    <form:input path="organization" type="hidden" value="${sessionScope.organization.id}"/>
                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <form:label path="person.firstName">Ad</form:label>
                                <form:input path="person.firstName" cssClass="form-control"
                                            placeholder="Adı daxil edin Məs: Səbuhi"/>
                                <form:errors path="person.firstName" cssClass="control-label alert-danger"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <form:label path="person.lastName">Soyad</form:label>
                                <form:input path="person.lastName" cssClass="form-control"
                                            placeholder="Soyadı daxil edin Məs: Vəliyev"/>
                                <form:errors path="person.lastName" cssClass="control-label alert-danger"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <form:label path="person.fatherName">Ata adı</form:label>
                                <form:input path="person.fatherName" cssClass="form-control"
                                            placeholder="Ata adını daxil edin"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <form:label path="person.birthday">Doğum tarixi</form:label>
                                <div class="input-group date">
                                    <form:input path="person.birthday" autocomplete="off"
                                                cssClass="form-control datepicker-element" date="date"
                                                placeholder="dd.MM.yyyy"/>
                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                                    </div>
                                </div>
                                <form:errors path="person.birthday" cssClass="control-label alert-danger"/>
                            </div>
                        </div>
                    </div>
                    <hr style="width: 100%"/>
                    <div class="row bg-light">
                        <div class="col-md-3">
                            <div class="form-group">
                                <form:label path="person.idCardSerialNumber">Ş.v - nin seriya nömrəsi</form:label>
                                <form:input path="person.idCardSerialNumber" cssClass="form-control" placeholder="AA0822304"/>
                                <form:errors path="person.idCardSerialNumber" cssClass="control-label alert-danger" />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <form:label path="person.idCardPinCode">Ş.v - nin pin kodu</form:label>
                                <form:input path="person.idCardPinCode" cssClass="form-control" placeholder="Məs: 4HWL0AM"/>
                                <form:errors path="person.idCardPinCode" cssClass="control-label alert-danger" />
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <form:label path="description">Açıqlama</form:label>
                                <form:textarea path="description" cssClass="form-control"/>
                                <form:errors path="description" cssClass="control-label alert-danger"/>
                            </div>
                        </div>
                    </div>
                    <hr style="width: 100%"/>
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <form:label path="person.contact.email">Email</form:label>
                                <div class="input-group">
                                    <form:input path="person.contact.email" cssClass="form-control"
                                                placeholder="example@example.com"/>
                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-at"></i>
                                        </span>
                                    </div>
                                </div>
                                <form:errors path="person.contact.email" cssClass="control-label alert-danger"/>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <form:label path="person.contact.mobilePhone">Mobil nömrə</form:label>
                                <div class="input-group">
                                    <form:input path="person.contact.mobilePhone" cssClass="form-control"
                                                placeholder="505505550"/>
                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-phone"></i>
                                        </span>
                                    </div>
                                </div>
                                <form:errors path="person.contact.mobilePhone" cssClass="control-label alert-danger"/>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <form:label path="person.contact.homePhone">Şəhər nömrəsi</form:label>
                                <div class="input-group">
                                    <form:input path="person.contact.homePhone" cssClass="form-control"
                                                placeholder="124555050"/>
                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-phone"></i>
                                        </span>
                                    </div>
                                </div>
                                <form:errors path="person.contact.homePhone" cssClass="control-label alert-danger"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <form:label path="person.contact.relationalPhoneNumber1">Əlaqəli şəxs nömrəsi #1</form:label>
                                <div class="input-group">
                                    <form:input path="person.contact.relationalPhoneNumber1" cssClass="form-control"
                                                placeholder="505505550"/>
                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-phone"></i>
                                        </span>
                                    </div>
                                </div>
                                <form:errors path="person.contact.relationalPhoneNumber1" cssClass="control-label alert-danger"/>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <form:label path="person.contact.relationalPhoneNumber2">Əlaqəli şəxs nömrəsi #2</form:label>
                                <div class="input-group">
                                    <form:input path="person.contact.relationalPhoneNumber2" cssClass="form-control"
                                                placeholder="505505550"/>
                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-phone"></i>
                                        </span>
                                    </div>
                                </div>
                                <form:errors path="person.contact.relationalPhoneNumber2" cssClass="control-label alert-danger"/>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <form:label path="person.contact.relationalPhoneNumber3">Əlaqəli şəxs nömrəsi #3</form:label>
                                <div class="input-group">
                                    <form:input path="person.contact.relationalPhoneNumber3" cssClass="form-control"
                                                placeholder="505505550"/>
                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-phone"></i>
                                        </span>
                                    </div>
                                </div>
                                <form:errors path="person.contact.relationalPhoneNumber3" cssClass="control-label alert-danger"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <form:label path="person.contact.city">Şəhər</form:label>
                                <form:select path="person.contact.city" cssClass="custom-select form-control">
                                    <form:options items="${cities}" itemLabel="name" itemValue="id"/>
                                </form:select>
                            </div>
                        </div>
                        <div class="col-md-8">
                            <div class="form-group">
                                <form:label path="person.contact.address">Ünvan</form:label>
                                <div class="input-group">
                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-street-view"></i>
                                        </span>
                                    </div>
                                    <form:input path="person.contact.address" cssClass="form-control"
                                                placeholder="Küçə adı, ev nömrəsi və s."/>
                                </div>
                                <form:errors path="person.contact.address" cssClass="control-label alert-danger"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <form:label path="person.contact.livingCity">Yaşadığı şəhər</form:label>
                                <form:select path="person.contact.livingCity" cssClass="custom-select form-control">
                                    <form:options items="${cities}" itemLabel="name" itemValue="id"/>
                                </form:select>
                            </div>
                        </div>
                        <div class="col-md-8">
                            <div class="form-group">
                                <form:label path="person.contact.livingAddress">Yaşadığı ünvan</form:label>
                                <div class="input-group">
                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-street-view"></i>
                                        </span>
                                    </div>
                                    <form:input path="person.contact.livingAddress" cssClass="form-control"
                                                placeholder="Küçə adı, ev nömrəsi və s."/>
                                </div>
                                <form:errors path="person.contact.livingAddress" cssClass="control-label alert-danger"/>
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

<script src="<c:url value="/assets/js/demo4/pages/crud/datatables/advanced/row-grouping.js" />"  type="text/javascript"></script>
<script>
    <c:if test="${edit.status}">
        $('#group_table tbody').on('dblclick', 'tr', function () {
            edit($('#form'), $(this).attr('data'), 'modal-operation', 'Redaktə');
        });
    </c:if>
</script>




