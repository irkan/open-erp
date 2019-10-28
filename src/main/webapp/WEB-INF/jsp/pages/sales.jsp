<%--
  Created by IntelliJ IDEA.
  User: irkan.ahmadov
  Date: 20.09.2019
  Time: 1:22
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="utl" uri="/WEB-INF/tld/Util.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link href="<c:url value="/assets/css/demo4/pages/wizard/wizard-1.css" />" rel="stylesheet" type="text/css"/>
<div class="kt-container  kt-grid__item kt-grid__item--fluid">
    <div class="row">
        <div class="col-lg-12">
            <div class="kt-portlet kt-portlet--mobile">
                <div class="kt-portlet__body">
                    Kontenti kodlayın!
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
            <div class="modal-body" style="padding: 0;">

            </div>
            <%--<div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="submit($('#form'));">Yadda saxla</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Bağla</button>
            </div>--%>


            <div class="kt-grid  kt-wizard-v1 kt-wizard-v1--white" id="kt_wizard_v1" data-ktwizard-state="step-first">
                <div class="kt-grid__item">

                    <!--begin: Form Wizard Nav -->
                    <div class="kt-wizard-v1__nav">
                        <div class="kt-wizard-v1__nav-items">
                            <div class="kt-wizard-v1__nav-item" data-ktwizard-type="step" data-ktwizard-state="current">
                                <div class="kt-wizard-v1__nav-body">
                                    <div class="kt-wizard-v1__nav-icon">
                                        <i class="flaticon-list"></i>
                                    </div>
                                    <div class="kt-wizard-v1__nav-label">
                                        1. Şəxsi məlumat
                                    </div>
                                </div>
                            </div>
                            <div class="kt-wizard-v1__nav-item" data-ktwizard-type="step">
                                <div class="kt-wizard-v1__nav-body">
                                    <div class="kt-wizard-v1__nav-icon">
                                        <i class="flaticon-bus-stop"></i>
                                    </div>
                                    <div class="kt-wizard-v1__nav-label">
                                        2. Əlaqə
                                    </div>
                                </div>
                            </div>
                            <div class="kt-wizard-v1__nav-item" data-ktwizard-type="step">
                                <div class="kt-wizard-v1__nav-body">
                                    <div class="kt-wizard-v1__nav-icon">
                                        <i class="flaticon-responsive"></i>
                                    </div>
                                    <div class="kt-wizard-v1__nav-label">
                                        3. İnventar
                                    </div>
                                </div>
                            </div>
                            <div class="kt-wizard-v1__nav-item" data-ktwizard-type="step">
                                <div class="kt-wizard-v1__nav-body">
                                    <div class="kt-wizard-v1__nav-icon">
                                        <i class="flaticon-truck"></i>
                                    </div>
                                    <div class="kt-wizard-v1__nav-label">
                                        4. Ödəmə
                                    </div>
                                </div>
                            </div>
                            <div class="kt-wizard-v1__nav-item" data-ktwizard-type="step">
                                <div class="kt-wizard-v1__nav-body">
                                    <div class="kt-wizard-v1__nav-icon">
                                        <i class="flaticon-globe"></i>
                                    </div>
                                    <div class="kt-wizard-v1__nav-label">
                                        5. Baxış
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!--end: Form Wizard Nav -->
                </div>
                <div class="kt-grid__item kt-grid__item--fluid kt-wizard-v1__wrapper">
                <form:form modelAttribute="form" id="kt_form" method="post" action="/sale/sales" cssClass="form-group kt-form">
                    <form:input path="id" type="hidden"/>
                        <div class="kt-wizard-v1__content" data-ktwizard-type="step-content" data-ktwizard-state="current">
                            <div class="kt-form__section kt-form__section--first">
                                <div class="kt-wizard-v1__form">
                                    <div class="row">
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <form:label path="customer.person.firstName">Ad</form:label>
                                                <form:input path="customer.person.firstName" cssClass="form-control" placeholder="Adı daxil edin Məs: Səbuhi"/>
                                                <form:errors path="customer.person.firstName" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <form:label path="customer.person.lastName">Soyad</form:label>
                                                <form:input path="customer.person.lastName" cssClass="form-control" placeholder="Soyadı daxil edin Məs: Vəliyev"/>
                                                <form:errors path="customer.person.lastName" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <form:label path="customer.person.fatherName">Ata adı</form:label>
                                                <form:input path="customer.person.fatherName" cssClass="form-control" placeholder="Ata adını daxil edin"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <form:label path="customer.person.birthday">Doğum tarixi</form:label>
                                                <div class="input-group date" >
                                                    <form:input path="customer.person.birthday" cssClass="form-control datepicker-element" date="date" placeholder="dd.MM.yyyy"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="customer.person.birthday" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <form:label path="customer.person.idCardSerialNumber">Ş.v - nin seriya nömrəsi</form:label>
                                                <form:input path="customer.person.idCardSerialNumber" cssClass="form-control" placeholder="AA0822304"/>
                                                <form:errors path="customer.person.idCardSerialNumber" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <form:label path="customer.person.idCardPinCode">Ş.v - nin pin kodu</form:label>
                                                <form:input path="customer.person.idCardPinCode" cssClass="form-control" placeholder="Məs: 4HWL0AM"/>
                                                <form:errors path="customer.person.idCardPinCode" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label>Ş.v-nin ön hissəsi</label>
                                                <div></div>
                                                <div class="custom-file">
                                                    <input type="file" name="file" class="custom-file-input" id="file1" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
                                                    <label class="custom-file-label" for="file1">Ş.v-nin ön hissəsi</label>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label>Ş.v-nin arxa hissəsi</label>
                                                <div></div>
                                                <div class="custom-file">
                                                    <input type="file" name="file" class="custom-file-input" id="file2" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
                                                    <label class="custom-file-label" for="file2">Ş.v-nin arxa hissəsi</label>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="kt-wizard-v1__content" data-ktwizard-type="step-content">
                            <div class="kt-form__section kt-form__section--first">
                                <div class="kt-wizard-v1__form">
                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <form:label path="customer.person.contact.mobilePhone">Mobil nömrə</form:label>
                                                <div class="input-group" >
                                                    <form:input path="customer.person.contact.mobilePhone" cssClass="form-control" placeholder="505505550"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-phone"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="customer.person.contact.mobilePhone" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <form:label path="customer.person.contact.homePhone">Şəhər nömrəsi</form:label>
                                                <div class="input-group" >
                                                    <form:input path="customer.person.contact.homePhone" cssClass="form-control" placeholder="124555050"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-phone"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="customer.person.contact.homePhone" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <form:label path="customer.person.contact.email">Email</form:label>
                                                <div class="input-group" >
                                                    <form:input path="customer.person.contact.email" cssClass="form-control" placeholder="example@example.com"/>
                                                    <div class="input-group-append">
                                                            <span class="input-group-text">
                                                                <i class="la la-at"></i>
                                                            </span>
                                                    </div>
                                                </div>
                                                <form:errors path="customer.person.contact.email" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <form:label path="customer.person.contact.city">Şəhər</form:label>
                                                <form:select  path="customer.person.contact.city" cssClass="custom-select form-control">
                                                    <form:options items="${cities}" itemLabel="name" itemValue="id" />
                                                </form:select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="form-group">
                                                <form:label path="customer.person.contact.address">Ünvan</form:label>
                                                <div class="input-group" >
                                                    <div class="input-group-append">
                                                        <span class="input-group-text">
                                                            <i class="la la-street-view"></i>
                                                        </span>
                                                    </div>
                                                    <form:input path="customer.person.contact.address" cssClass="form-control" placeholder="Küçə adı, ev nömrəsi və s."/>
                                                </div>
                                                <form:errors path="customer.person.contact.address" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="form-group">
                                                <form:label path="customer.person.contact.livingAddress">Yaşayış ünvanı</form:label>
                                                <div class="input-group" >
                                                    <div class="input-group-append">
                                                        <span class="input-group-text">
                                                            <i class="la la-street-view"></i>
                                                        </span>
                                                    </div>
                                                    <form:input path="customer.person.contact.livingAddress" cssClass="form-control" placeholder="Küçə adı, ev nömrəsi və s."/>
                                                </div>
                                                <form:errors path="customer.person.contact.livingAddress" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!--end: Form Wizard Step 2-->

                        <!--begin: Form Wizard Step 3-->
                        <div class="kt-wizard-v1__content" data-ktwizard-type="step-content">
                            <div class="kt-form__section kt-form__section--first">
                                <div class="kt-wizard-v1__form">
                                    <div class="form-group">
                                        <label>Delivery Type</label>
                                        <select name="delivery" class="form-control">
                                            <option value="">Select a Service Type Option</option>
                                            <option value="overnight" selected>Overnight Delivery (within 48 hours)</option>
                                            <option value="express">Express Delivery (within 5 working days)</option>
                                            <option value="basic">Basic Delivery (within 5 - 10 working days)</option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label>Packaging Type</label>
                                        <select name="packaging" class="form-control">
                                            <option value="">Select a Packaging Type Option</option>
                                            <option value="regular" selected>Regular Packaging</option>
                                            <option value="oversized">Oversized Packaging</option>
                                            <option value="fragile">Fragile Packaging</option>
                                            <option value="frozen">Frozen Packaging</option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label>Preferred Delivery Window</label>
                                        <select name="preferreddelivery" class="form-control">
                                            <option value="">Select a Preferred Delivery Option</option>
                                            <option value="morning" selected>Morning Delivery (8:00AM - 11:00AM)</option>
                                            <option value="afternoon">Afternoon Delivery (11:00AM - 3:00PM)</option>
                                            <option value="evening">Evening Delivery (3:00PM - 7:00PM)</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!--end: Form Wizard Step 3-->

                        <!--begin: Form Wizard Step 4-->
                        <div class="kt-wizard-v1__content" data-ktwizard-type="step-content">
                            <div class="kt-form__section kt-form__section--first">
                                <div class="kt-wizard-v1__form">
                                    <div class="form-group">
                                        <label>Address Line 1</label>
                                        <input type="text" class="form-control" name="locaddress1" placeholder="Address Line 1" value="Address Line 1">
                                        <span class="form-text text-muted">Please enter your Address.</span>
                                    </div>
                                    <div class="form-group">
                                        <label>Address Line 2</label>
                                        <input type="text" class="form-control" name="locaddress2" placeholder="Address Line 2" value="Address Line 2">
                                        <span class="form-text text-muted">Please enter your Address.</span>
                                    </div>
                                    <div class="row">
                                        <div class="col-xl-6">
                                            <div class="form-group">
                                                <label>Postcode</label>
                                                <input type="text" class="form-control" name="locpostcode" placeholder="Postcode" value="3072">
                                                <span class="form-text text-muted">Please enter your Postcode.</span>
                                            </div>
                                        </div>
                                        <div class="col-xl-6">
                                            <div class="form-group">
                                                <label>City</label>
                                                <input type="text" class="form-control" name="loccity" placeholder="City" value="Preston">
                                                <span class="form-text text-muted">Please enter your City.</span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-xl-6">
                                            <div class="form-group">
                                                <label>State</label>
                                                <input type="text" class="form-control" name="locstate" placeholder="State" value="VIC">
                                                <span class="form-text text-muted">Please enter your Postcode.</span>
                                            </div>
                                        </div>
                                        <div class="col-xl-6">
                                            <div class="form-group">
                                                <label>Country:</label>
                                                <select name="loccountry" class="form-control">
                                                    <option value="">Select</option>
                                                    <option value="AF">Afghanistan</option>
                                                    <option value="AX">Åland Islands</option>
                                                    <option value="AL">Albania</option>
                                                    <option value="DZ">Algeria</option>
                                                    <option value="AS">American Samoa</option>
                                                    <option value="AD">Andorra</option>
                                                    <option value="AO">Angola</option>
                                                    <option value="AI">Anguilla</option>
                                                    <option value="AQ">Antarctica</option>
                                                    <option value="AG">Antigua and Barbuda</option>
                                                    <option value="AR">Argentina</option>
                                                    <option value="AM">Armenia</option>
                                                    <option value="AW">Aruba</option>
                                                    <option value="AU" selected>Australia</option>
                                                    <option value="AT">Austria</option>
                                                    <option value="AZ">Azerbaijan</option>
                                                    <option value="BS">Bahamas</option>
                                                    <option value="BH">Bahrain</option>
                                                    <option value="BD">Bangladesh</option>
                                                    <option value="BB">Barbados</option>
                                                    <option value="BY">Belarus</option>
                                                    <option value="BE">Belgium</option>
                                                    <option value="BZ">Belize</option>
                                                    <option value="BJ">Benin</option>
                                                    <option value="BM">Bermuda</option>
                                                    <option value="BT">Bhutan</option>
                                                    <option value="BO">Bolivia, Plurinational State of</option>
                                                    <option value="BQ">Bonaire, Sint Eustatius and Saba</option>
                                                    <option value="BA">Bosnia and Herzegovina</option>
                                                    <option value="BW">Botswana</option>
                                                    <option value="BV">Bouvet Island</option>
                                                    <option value="BR">Brazil</option>
                                                    <option value="IO">British Indian Ocean Territory</option>
                                                    <option value="BN">Brunei Darussalam</option>
                                                    <option value="BG">Bulgaria</option>
                                                    <option value="BF">Burkina Faso</option>
                                                    <option value="BI">Burundi</option>
                                                    <option value="KH">Cambodia</option>
                                                    <option value="CM">Cameroon</option>
                                                    <option value="CA">Canada</option>
                                                    <option value="CV">Cape Verde</option>
                                                    <option value="KY">Cayman Islands</option>
                                                    <option value="CF">Central African Republic</option>
                                                    <option value="TD">Chad</option>
                                                    <option value="CL">Chile</option>
                                                    <option value="CN">China</option>
                                                    <option value="CX">Christmas Island</option>
                                                    <option value="CC">Cocos (Keeling) Islands</option>
                                                    <option value="CO">Colombia</option>
                                                    <option value="KM">Comoros</option>
                                                    <option value="CG">Congo</option>
                                                    <option value="CD">Congo, the Democratic Republic of the</option>
                                                    <option value="CK">Cook Islands</option>
                                                    <option value="CR">Costa Rica</option>
                                                    <option value="CI">Côte d'Ivoire</option>
                                                    <option value="HR">Croatia</option>
                                                    <option value="CU">Cuba</option>
                                                    <option value="CW">Curaçao</option>
                                                    <option value="CY">Cyprus</option>
                                                    <option value="CZ">Czech Republic</option>
                                                    <option value="DK">Denmark</option>
                                                    <option value="DJ">Djibouti</option>
                                                    <option value="DM">Dominica</option>
                                                    <option value="DO">Dominican Republic</option>
                                                    <option value="EC">Ecuador</option>
                                                    <option value="EG">Egypt</option>
                                                    <option value="SV">El Salvador</option>
                                                    <option value="GQ">Equatorial Guinea</option>
                                                    <option value="ER">Eritrea</option>
                                                    <option value="EE">Estonia</option>
                                                    <option value="ET">Ethiopia</option>
                                                    <option value="FK">Falkland Islands (Malvinas)</option>
                                                    <option value="FO">Faroe Islands</option>
                                                    <option value="FJ">Fiji</option>
                                                    <option value="FI">Finland</option>
                                                    <option value="FR">France</option>
                                                    <option value="GF">French Guiana</option>
                                                    <option value="PF">French Polynesia</option>
                                                    <option value="TF">French Southern Territories</option>
                                                    <option value="GA">Gabon</option>
                                                    <option value="GM">Gambia</option>
                                                    <option value="GE">Georgia</option>
                                                    <option value="DE">Germany</option>
                                                    <option value="GH">Ghana</option>
                                                    <option value="GI">Gibraltar</option>
                                                    <option value="GR">Greece</option>
                                                    <option value="GL">Greenland</option>
                                                    <option value="GD">Grenada</option>
                                                    <option value="GP">Guadeloupe</option>
                                                    <option value="GU">Guam</option>
                                                    <option value="GT">Guatemala</option>
                                                    <option value="GG">Guernsey</option>
                                                    <option value="GN">Guinea</option>
                                                    <option value="GW">Guinea-Bissau</option>
                                                    <option value="GY">Guyana</option>
                                                    <option value="HT">Haiti</option>
                                                    <option value="HM">Heard Island and McDonald Islands</option>
                                                    <option value="VA">Holy See (Vatican City State)</option>
                                                    <option value="HN">Honduras</option>
                                                    <option value="HK">Hong Kong</option>
                                                    <option value="HU">Hungary</option>
                                                    <option value="IS">Iceland</option>
                                                    <option value="IN">India</option>
                                                    <option value="ID">Indonesia</option>
                                                    <option value="IR">Iran, Islamic Republic of</option>
                                                    <option value="IQ">Iraq</option>
                                                    <option value="IE">Ireland</option>
                                                    <option value="IM">Isle of Man</option>
                                                    <option value="IL">Israel</option>
                                                    <option value="IT">Italy</option>
                                                    <option value="JM">Jamaica</option>
                                                    <option value="JP">Japan</option>
                                                    <option value="JE">Jersey</option>
                                                    <option value="JO">Jordan</option>
                                                    <option value="KZ">Kazakhstan</option>
                                                    <option value="KE">Kenya</option>
                                                    <option value="KI">Kiribati</option>
                                                    <option value="KP">Korea, Democratic People's Republic of</option>
                                                    <option value="KR">Korea, Republic of</option>
                                                    <option value="KW">Kuwait</option>
                                                    <option value="KG">Kyrgyzstan</option>
                                                    <option value="LA">Lao People's Democratic Republic</option>
                                                    <option value="LV">Latvia</option>
                                                    <option value="LB">Lebanon</option>
                                                    <option value="LS">Lesotho</option>
                                                    <option value="LR">Liberia</option>
                                                    <option value="LY">Libya</option>
                                                    <option value="LI">Liechtenstein</option>
                                                    <option value="LT">Lithuania</option>
                                                    <option value="LU">Luxembourg</option>
                                                    <option value="MO">Macao</option>
                                                    <option value="MK">Macedonia, the former Yugoslav Republic of</option>
                                                    <option value="MG">Madagascar</option>
                                                    <option value="MW">Malawi</option>
                                                    <option value="MY">Malaysia</option>
                                                    <option value="MV">Maldives</option>
                                                    <option value="ML">Mali</option>
                                                    <option value="MT">Malta</option>
                                                    <option value="MH">Marshall Islands</option>
                                                    <option value="MQ">Martinique</option>
                                                    <option value="MR">Mauritania</option>
                                                    <option value="MU">Mauritius</option>
                                                    <option value="YT">Mayotte</option>
                                                    <option value="MX">Mexico</option>
                                                    <option value="FM">Micronesia, Federated States of</option>
                                                    <option value="MD">Moldova, Republic of</option>
                                                    <option value="MC">Monaco</option>
                                                    <option value="MN">Mongolia</option>
                                                    <option value="ME">Montenegro</option>
                                                    <option value="MS">Montserrat</option>
                                                    <option value="MA">Morocco</option>
                                                    <option value="MZ">Mozambique</option>
                                                    <option value="MM">Myanmar</option>
                                                    <option value="NA">Namibia</option>
                                                    <option value="NR">Nauru</option>
                                                    <option value="NP">Nepal</option>
                                                    <option value="NL">Netherlands</option>
                                                    <option value="NC">New Caledonia</option>
                                                    <option value="NZ">New Zealand</option>
                                                    <option value="NI">Nicaragua</option>
                                                    <option value="NE">Niger</option>
                                                    <option value="NG">Nigeria</option>
                                                    <option value="NU">Niue</option>
                                                    <option value="NF">Norfolk Island</option>
                                                    <option value="MP">Northern Mariana Islands</option>
                                                    <option value="NO">Norway</option>
                                                    <option value="OM">Oman</option>
                                                    <option value="PK">Pakistan</option>
                                                    <option value="PW">Palau</option>
                                                    <option value="PS">Palestinian Territory, Occupied</option>
                                                    <option value="PA">Panama</option>
                                                    <option value="PG">Papua New Guinea</option>
                                                    <option value="PY">Paraguay</option>
                                                    <option value="PE">Peru</option>
                                                    <option value="PH">Philippines</option>
                                                    <option value="PN">Pitcairn</option>
                                                    <option value="PL">Poland</option>
                                                    <option value="PT">Portugal</option>
                                                    <option value="PR">Puerto Rico</option>
                                                    <option value="QA">Qatar</option>
                                                    <option value="RE">Réunion</option>
                                                    <option value="RO">Romania</option>
                                                    <option value="RU">Russian Federation</option>
                                                    <option value="RW">Rwanda</option>
                                                    <option value="BL">Saint Barthélemy</option>
                                                    <option value="SH">Saint Helena, Ascension and Tristan da Cunha</option>
                                                    <option value="KN">Saint Kitts and Nevis</option>
                                                    <option value="LC">Saint Lucia</option>
                                                    <option value="MF">Saint Martin (French part)</option>
                                                    <option value="PM">Saint Pierre and Miquelon</option>
                                                    <option value="VC">Saint Vincent and the Grenadines</option>
                                                    <option value="WS">Samoa</option>
                                                    <option value="SM">San Marino</option>
                                                    <option value="ST">Sao Tome and Principe</option>
                                                    <option value="SA">Saudi Arabia</option>
                                                    <option value="SN">Senegal</option>
                                                    <option value="RS">Serbia</option>
                                                    <option value="SC">Seychelles</option>
                                                    <option value="SL">Sierra Leone</option>
                                                    <option value="SG">Singapore</option>
                                                    <option value="SX">Sint Maarten (Dutch part)</option>
                                                    <option value="SK">Slovakia</option>
                                                    <option value="SI">Slovenia</option>
                                                    <option value="SB">Solomon Islands</option>
                                                    <option value="SO">Somalia</option>
                                                    <option value="ZA">South Africa</option>
                                                    <option value="GS">South Georgia and the South Sandwich Islands</option>
                                                    <option value="SS">South Sudan</option>
                                                    <option value="ES">Spain</option>
                                                    <option value="LK">Sri Lanka</option>
                                                    <option value="SD">Sudan</option>
                                                    <option value="SR">Suriname</option>
                                                    <option value="SJ">Svalbard and Jan Mayen</option>
                                                    <option value="SZ">Swaziland</option>
                                                    <option value="SE">Sweden</option>
                                                    <option value="CH">Switzerland</option>
                                                    <option value="SY">Syrian Arab Republic</option>
                                                    <option value="TW">Taiwan, Province of China</option>
                                                    <option value="TJ">Tajikistan</option>
                                                    <option value="TZ">Tanzania, United Republic of</option>
                                                    <option value="TH">Thailand</option>
                                                    <option value="TL">Timor-Leste</option>
                                                    <option value="TG">Togo</option>
                                                    <option value="TK">Tokelau</option>
                                                    <option value="TO">Tonga</option>
                                                    <option value="TT">Trinidad and Tobago</option>
                                                    <option value="TN">Tunisia</option>
                                                    <option value="TR">Turkey</option>
                                                    <option value="TM">Turkmenistan</option>
                                                    <option value="TC">Turks and Caicos Islands</option>
                                                    <option value="TV">Tuvalu</option>
                                                    <option value="UG">Uganda</option>
                                                    <option value="UA">Ukraine</option>
                                                    <option value="AE">United Arab Emirates</option>
                                                    <option value="GB">United Kingdom</option>
                                                    <option value="US">United States</option>
                                                    <option value="UM">United States Minor Outlying Islands</option>
                                                    <option value="UY">Uruguay</option>
                                                    <option value="UZ">Uzbekistan</option>
                                                    <option value="VU">Vanuatu</option>
                                                    <option value="VE">Venezuela, Bolivarian Republic of</option>
                                                    <option value="VN">Viet Nam</option>
                                                    <option value="VG">Virgin Islands, British</option>
                                                    <option value="VI">Virgin Islands, U.S.</option>
                                                    <option value="WF">Wallis and Futuna</option>
                                                    <option value="EH">Western Sahara</option>
                                                    <option value="YE">Yemen</option>
                                                    <option value="ZM">Zambia</option>
                                                    <option value="ZW">Zimbabwe</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!--end: Form Wizard Step 4-->

                        <!--begin: Form Wizard Step 5-->
                        <div class="kt-wizard-v1__content" data-ktwizard-type="step-content">
                            <div class="kt-heading kt-heading--md">Review your Details and Submit</div>
                            <div class="kt-form__section kt-form__section--first">
                                <div class="kt-wizard-v1__review">
                                    <div class="kt-wizard-v1__review-item">
                                        <div class="kt-wizard-v1__review-title">
                                            Current Address
                                        </div>
                                        <div class="kt-wizard-v1__review-content">
                                            Address Line 1<br />
                                            Address Line 2<br />
                                            Melbourne 3000, VIC, Australia
                                        </div>
                                    </div>
                                    <div class="kt-wizard-v1__review-item">
                                        <div class="kt-wizard-v1__review-title">
                                            Delivery Details
                                        </div>
                                        <div class="kt-wizard-v1__review-content">
                                            Package: Complete Workstation (Monitor, Computer, Keyboard & Mouse)<br />
                                            Weight: 25kg<br />
                                            Dimensions: 110cm (w) x 90cm (h) x 150cm (L)
                                        </div>
                                    </div>
                                    <div class="kt-wizard-v1__review-item">
                                        <div class="kt-wizard-v1__review-title">
                                            Delivery Service Type
                                        </div>
                                        <div class="kt-wizard-v1__review-content">
                                            Overnight Delivery with Regular Packaging<br />
                                            Preferred Morning (8:00AM - 11:00AM) Delivery
                                        </div>
                                    </div>
                                    <div class="kt-wizard-v1__review-item">
                                        <div class="kt-wizard-v1__review-title">
                                            Delivery Address
                                        </div>
                                        <div class="kt-wizard-v1__review-content">
                                            Address Line 1<br />
                                            Address Line 2<br />
                                            Preston 3072, VIC, Australia
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="kt-form__actions">
                            <button class="btn btn-secondary btn-md btn-tall btn-wide kt-font-bold kt-font-transform-u" data-ktwizard-type="action-prev">
                                Geri
                            </button>
                            <button class="btn btn-success btn-md btn-tall btn-wide kt-font-bold kt-font-transform-u" data-ktwizard-type="action-submit">
                                Yadda saxla
                            </button>
                            <button class="btn btn-brand btn-md btn-tall btn-wide kt-font-bold kt-font-transform-u" data-ktwizard-type="action-next">
                                İrəli
                            </button>
                        </div>
                    </form:form>
                </div>
            </div>

        </div>
    </div>
</div>

<script>
    $('.custom-file-input').on('change', function() {
        var fileName = $(this).val();
        $(this).next('.custom-file-label').addClass("selected").html(fileName);
    });

    var KTWizard1 = function () {
        // Base elements
        var wizardEl;
        var formEl;
        var validator;
        var wizard;

        // Private functions
        var initWizard = function () {
            // Initialize form wizard
            wizard = new KTWizard('kt_wizard_v1', {
                startStep: 1
            });

            // Validation before going to next page
            wizard.on('beforeNext', function(wizardObj) {
                if (validator.form() !== true) {
                    wizardObj.stop();  // don't go to the next step
                }
            });

            wizard.on('beforePrev', function(wizardObj) {
                if (validator.form() !== true) {
                    wizardObj.stop();  // don't go to the next step
                }
            });

            // Change event
            wizard.on('change', function(wizard) {
                setTimeout(function() {
                    KTUtil.scrollTop();
                }, 500);
            });
        }

        var initValidation = function() {
            validator = formEl.validate({
                // Validate only visible fields
                ignore: ":hidden",

                // Validation rules
                rules: {
                    //= Step 1
                    address1: {
                        required: true
                    },
                    postcode: {
                        required: true
                    },
                    city: {
                        required: true
                    },
                    state: {
                        required: true
                    },
                    country: {
                        required: true
                    },

                    //= Step 2
                    package: {
                        required: true
                    },
                    weight: {
                        required: true
                    },
                    width: {
                        required: true
                    },
                    height: {
                        required: true
                    },
                    length: {
                        required: true
                    },

                    //= Step 3
                    delivery: {
                        required: true
                    },
                    packaging: {
                        required: true
                    },
                    preferreddelivery: {
                        required: true
                    },

                    //= Step 4
                    locaddress1: {
                        required: true
                    },
                    locpostcode: {
                        required: true
                    },
                    loccity: {
                        required: true
                    },
                    locstate: {
                        required: true
                    },
                    loccountry: {
                        required: true
                    },
                },

                // Display error
                invalidHandler: function(event, validator) {
                    KTUtil.scrollTop();

                    swal.fire({
                        "title": "",
                        "text": "There are some errors in your submission. Please correct them.",
                        "type": "error",
                        "confirmButtonClass": "btn btn-secondary"
                    });
                },

                // Submit valid form
                submitHandler: function (form) {

                }
            });
        }

        var initSubmit = function() {
            var btn = formEl.find('[data-ktwizard-type="action-submit"]');

            btn.on('click', function(e) {
                e.preventDefault();

                if (validator.form()) {
                    // See: src\js\framework\base\app.js
                    KTApp.progress(btn);
                    //KTApp.block(formEl);

                    // See: http://malsup.com/jquery/form/#ajaxSubmit
                    formEl.ajaxSubmit({
                        success: function() {
                            KTApp.unprogress(btn);
                            //KTApp.unblock(formEl);

                            swal.fire({
                                "title": "",
                                "text": "The application has been successfully submitted!",
                                "type": "success",
                                "confirmButtonClass": "btn btn-secondary"
                            });
                        }
                    });
                }
            });
        }

        return {
            // public functions
            init: function() {
                wizardEl = KTUtil.get('kt_wizard_v1');
                formEl = $('#kt_form');

                initWizard();
                initValidation();
                initSubmit();
            }
        };
    }();

    jQuery(document).ready(function() {
        KTWizard1.init();
    });
</script>



