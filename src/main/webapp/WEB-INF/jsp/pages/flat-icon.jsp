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
        <div class="col-md-12">
            <!--begin::Portlet-->
            <div class="kt-portlet">
                <div class="kt-portlet__head">
                    <div class="kt-portlet__head-label">
							<span class="kt-portlet__head-icon kt-hidden">
								<i class="la la-gear"></i>
							</span>
                        <h3 class="kt-portlet__head-title">
                            Flaticon Icons
                        </h3>
                    </div>
                </div>
                <div class="kt-portlet__body">
                    <div class="row"><div class="col-md-2">
                        <div class="kt-demo-icon">
                            <div class="kt-demo-icon__preview">
                                <i class="flaticon-email-black-circular-button"></i>
                            </div>
                            <div class="kt-demo-icon__class">
                                flaticon-email-black-circular-button						</div>
                        </div>
                    </div><div class="col-md-2">
                        <div class="kt-demo-icon">
                            <div class="kt-demo-icon__preview">
                                <i class="flaticon-map"></i>
                            </div>
                            <div class="kt-demo-icon__class">
                                flaticon-map						</div>
                        </div>
                    </div><div class="col-md-2">
                        <div class="kt-demo-icon">
                            <div class="kt-demo-icon__preview">
                                <i class="flaticon-alert-off"></i>
                            </div>
                            <div class="kt-demo-icon__class">
                                flaticon-alert-off						</div>
                        </div>
                    </div><div class="col-md-2">
                        <div class="kt-demo-icon">
                            <div class="kt-demo-icon__preview">
                                <i class="flaticon-alert"></i>
                            </div>
                            <div class="kt-demo-icon__class">
                                flaticon-alert						</div>
                        </div>
                    </div><div class="col-md-2">
                        <div class="kt-demo-icon">
                            <div class="kt-demo-icon__preview">
                                <i class="flaticon-computer"></i>
                            </div>
                            <div class="kt-demo-icon__class">
                                flaticon-computer						</div>
                        </div>
                    </div><div class="col-md-2">
                        <div class="kt-demo-icon">
                            <div class="kt-demo-icon__preview">
                                <i class="flaticon-responsive"></i>
                            </div>
                            <div class="kt-demo-icon__class">
                                flaticon-responsive						</div>
                        </div>
                    </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-presentation"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-presentation						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-arrows"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-arrows						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-rocket"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-rocket						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-reply"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-reply						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-gift"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-gift						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-confetti"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-confetti						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-piggy-bank"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-piggy-bank						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-support"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-support						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-delete"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-delete						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-eye"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-eye						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-multimedia"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-multimedia						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-whatsapp"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-whatsapp						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-multimedia-2"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-multimedia-2						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-email"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-email						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-presentation-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-presentation-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-trophy"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-trophy						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-psd"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-psd						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-layer"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-layer						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-doc"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-doc						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-file"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-file						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-network"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-network						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-bus-stop"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-bus-stop						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-globe"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-globe						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-upload"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-upload						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-squares"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-squares						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-technology"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-technology						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-up-arrow"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-up-arrow						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-browser"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-browser						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-speech-bubble"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-speech-bubble						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-coins"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-coins						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-open-box"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-open-box						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-speech-bubble-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-speech-bubble-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-attachment"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-attachment						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-photo-camera"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-photo-camera						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-skype-logo"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-skype-logo						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-linkedin-logo"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-linkedin-logo						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-twitter-logo"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-twitter-logo						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-facebook-letter-logo"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-facebook-letter-logo						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-calendar-with-a-clock-time-tools"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-calendar-with-a-clock-time-tools						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-youtube"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-youtube						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-add-circular-button"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-add-circular-button						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-more-v2"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-more-v2						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-search"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-search						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-search-magnifier-interface-symbol"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-search-magnifier-interface-symbol						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-questions-circular-button"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-questions-circular-button						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-refresh"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-refresh						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-logout"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-logout						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-event-calendar-symbol"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-event-calendar-symbol						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-laptop"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-laptop						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-tool"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-tool						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-graphic"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-graphic						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-symbol"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-symbol						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-graphic-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-graphic-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-clock"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-clock						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-squares-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-squares-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-black"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-black						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-book"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-book						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-cogwheel"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-cogwheel						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-exclamation"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-exclamation						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-add-label-button"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-add-label-button						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-delete-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-delete-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-interface"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-interface						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-more"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-more						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-warning-sign"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-warning-sign						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-calendar"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-calendar						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-instagram-logo"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-instagram-logo						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-linkedin"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-linkedin						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-facebook-logo-button"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-facebook-logo-button						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-twitter-logo-button"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-twitter-logo-button						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-cancel"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-cancel						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-exclamation-square"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-exclamation-square						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-buildings"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-buildings						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-danger"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-danger						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-technology-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-technology-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-letter-g"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-letter-g						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-interface-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-interface-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-circle"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-circle						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-pin"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-pin						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-close"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-close						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-clock-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-clock-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-apps"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-apps						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-user"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-user						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-menu-button"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-menu-button						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-settings"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-settings						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-home"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-home						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-clock-2"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-clock-2						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-lifebuoy"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-lifebuoy						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-cogwheel-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-cogwheel-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-paper-plane"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-paper-plane						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-statistics"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-statistics						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-diagram"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-diagram						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-line-graph"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-line-graph						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-customer"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-customer						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-visible"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-visible						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-shopping-basket"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-shopping-basket						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-price-tag"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-price-tag						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-businesswoman"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-businesswoman						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-medal"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-medal						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-like"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-like						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-edit"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-edit						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-avatar"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-avatar						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-download"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-download						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-home-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-home-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-mail"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-mail						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-mail-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-mail-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-warning"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-warning						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-cart"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-cart						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-bag"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-bag						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-pie-chart"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-pie-chart						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-graph"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-graph						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-interface-2"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-interface-2						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-chat"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-chat						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-envelope"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-envelope						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-chat-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-chat-1						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-interface-3"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-interface-3						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-background"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-background						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-file-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-file-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-interface-4"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-interface-4						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-multimedia-3"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-multimedia-3						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-list"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-list						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-time"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-time						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-profile"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-profile						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-imac"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-imac						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-medical"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-medical						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-music"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-music						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-plus"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-plus						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-exclamation-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-exclamation-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-info"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-info						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-menu-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-menu-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-menu-2"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-menu-2						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-share"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-share						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-interface-5"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-interface-5						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-signs"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-signs						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-tabs"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-tabs						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-multimedia-4"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-multimedia-4						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-upload-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-upload-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-web"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-web						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-placeholder"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-placeholder						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-placeholder-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-placeholder-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-layers"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-layers						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-interface-6"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-interface-6						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-interface-7"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-interface-7						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-interface-8"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-interface-8						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-tool-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-tool-1						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-settings-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-settings-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-alarm"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-alarm						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-search-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-search-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-time-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-time-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-stopwatch"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-stopwatch						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-folder"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-folder						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-folder-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-folder-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-folder-2"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-folder-2						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-folder-3"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-folder-3						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-file-2"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-file-2						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-list-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-list-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-list-2"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-list-2						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-calendar-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-calendar-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-time-2"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-time-2						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-interface-9"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-interface-9						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-app"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-app						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-suitcase"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-suitcase						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-grid-menu-v2"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-grid-menu-v2						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-more-v6"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-more-v6						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-more-v5"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-more-v5						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-add"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-add						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-multimedia-5"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-multimedia-5						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-more-v4"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-more-v4						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-placeholder-2"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-placeholder-2						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-map-location"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-map-location						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-users"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-users						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-profile-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-profile-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-lock"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-lock						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-sound"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-sound						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-star"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-star						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-placeholder-3"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-placeholder-3						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-bell"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-bell						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-paper-plane-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-paper-plane-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-users-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-users-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-more-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-more-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-up-arrow-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-up-arrow-1						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-grid-menu"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-grid-menu						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-alarm-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-alarm-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-earth-globe"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-earth-globe						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-alert-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-alert-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-internet"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-internet						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-user-ok"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-user-ok						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-user-add"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-user-add						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-user-settings"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-user-settings						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-truck"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-truck						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-analytics"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-analytics						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-notes"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-notes						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-tea-cup"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-tea-cup						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-exclamation-2"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-exclamation-2						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-technology-2"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-technology-2						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-location"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-location						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-edit-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-edit-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-home-2"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-home-2						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-dashboard"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-dashboard						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-information"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-information						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-light"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-light						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-car"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-car						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-business"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-business						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-squares-2"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-squares-2						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-signs-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-signs-1						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-mark"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-mark						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-squares-3"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-squares-3						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-comment"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-comment						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-shapes"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-shapes						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-clipboard"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-clipboard						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-squares-4"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-squares-4						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-delete-2"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-delete-2						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-bell-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-bell-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-list-3"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-list-3						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-infinity"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-infinity						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-chat-2"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-chat-2						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-calendar-2"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-calendar-2						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-signs-2"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-signs-2						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-time-3"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-time-3						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-calendar-3"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-calendar-3						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-interface-10"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-interface-10						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-interface-11"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-interface-11						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-folder-4"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-folder-4						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-alert-2"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-alert-2						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-cogwheel-2"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-cogwheel-2						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-graphic-2"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-graphic-2						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-rotate"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-rotate						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-feed"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-feed						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-safe-shield-protection"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-safe-shield-protection						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-security"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-security						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-download-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-download-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-pie-chart-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-pie-chart-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon-notepad"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon-notepad						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-notification"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-notification						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-settings"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-settings						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-search"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-search						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-delete"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-delete						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-psd"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-psd						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-list"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-list						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-box"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-box						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-download"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-download						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-shield"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-shield						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-paperplane"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-paperplane						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-avatar"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-avatar						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-bell"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-bell						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-fax"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-fax						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-chart2"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-chart2						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-supermarket"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-supermarket						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-phone"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-phone						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-envelope"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-envelope						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-pin"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-pin						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-chat"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-chat						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-chart"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-chart						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-infographic"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-infographic						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-grids"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-grids						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-menu"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-menu						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-plus"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-plus						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-list-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-list-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-talk"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-talk						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-file"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-file						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-user"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-user						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-line-chart"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-line-chart						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-percentage"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-percentage						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-menu-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-menu-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-paper-plane"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-paper-plane						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-menu-2"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-menu-2						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-shopping-cart"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-shopping-cart						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-pie-chart"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-pie-chart						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-box-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-box-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-map"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-map						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-favourite"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-favourite						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-checking"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-checking						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-safe"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-safe						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-heart-rate-monitor"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-heart-rate-monitor						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-layers"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-layers						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-delivery-package"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-delivery-package						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-sms"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-sms						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-image-file"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-image-file						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-plus-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-plus-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-send"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-send						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-graphic-design"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-graphic-design						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-cup"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-cup						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-website"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-website						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-gift"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-gift						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-chronometer"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-chronometer						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-browser"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-browser						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-digital-marketing"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-digital-marketing						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-calendar"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-calendar						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-calendar-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-calendar-1						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-rocket"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-rocket						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-analytics"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-analytics						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-pie-chart-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-pie-chart-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-pie-chart-2"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-pie-chart-2						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-analytics-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-analytics-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-google-drive-file"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-google-drive-file						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-pie-chart-3"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-pie-chart-3						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-poll-symbol"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-poll-symbol						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-gear"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-gear						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-magnifier-tool"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-magnifier-tool						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-add"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-add						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-cube"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-cube						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-gift-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-gift-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-list-2"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-list-2						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-shopping-cart-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-shopping-cart-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-calendar-2"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-calendar-2						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-laptop"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-laptop						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-cube-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-cube-1						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-layers-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-layers-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-chat-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-chat-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-copy"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-copy						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-paper"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-paper						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-hospital"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-hospital						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-calendar-3"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-calendar-3						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-speaker"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-speaker						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-pie-chart-4"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-pie-chart-4						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-schedule"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-schedule						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-expand"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-expand						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-menu-3"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-menu-3						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-download-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-download-1						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-help"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-help						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-list-3"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-list-3						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-notepad"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-notepad						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-graph"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-graph						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-browser-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-browser-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-photograph"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-photograph						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-browser-2"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-browser-2						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-hourglass"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-hourglass						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-mail"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-mail						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-cardiogram"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-cardiogram						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-document"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-document						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-contract"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-contract						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-graph-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-graph-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-graphic"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-graphic						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-position"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-position						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-soft-icons"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-soft-icons						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-circle-vol-2"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-circle-vol-2						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-rocket-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-rocket-1						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-lorry"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-lorry						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-cd"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-cd						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-file-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-file-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-reload"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-reload						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-placeholder"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-placeholder						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-refresh"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-refresh						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-medical-records"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-medical-records						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-rectangular"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-rectangular						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-medical-records-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-medical-records-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-indent-dots"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-indent-dots						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-search-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-search-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-edit"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-edit						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-new-email"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-new-email						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-calendar-4"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-calendar-4						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-console"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-console						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-open-text-book"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-open-text-book						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-download-2"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-download-2						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-zig-zag-line-sign"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-zig-zag-line-sign						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-tools-and-utensils"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-tools-and-utensils						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-crisp-icons"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-crisp-icons						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-trash"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-trash						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-lock"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-lock						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-bell-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-bell-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-setup"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-setup						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-menu-4"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-menu-4						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-architecture-and-city"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-architecture-and-city						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-shelter"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-shelter						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-add-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-add-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-checkmark"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-checkmark						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-circular-arrow"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-circular-arrow						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-user-outline-symbol"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-user-outline-symbol						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-rhombus"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-rhombus						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-crisp-icons-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-crisp-icons-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-soft-icons-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-soft-icons-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-hexagonal"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-hexagonal						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-time"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-time						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-contrast"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-contrast						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-telegram-logo"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-telegram-logo						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-hangouts-logo"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-hangouts-logo						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-analytics-2"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-analytics-2						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-wifi"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-wifi						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-protected"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-protected						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-drop"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-drop						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-mail-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-mail-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-delivery-truck"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-delivery-truck						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-writing"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-writing						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-calendar-5"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-calendar-5						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-protection"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-protection						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-calendar-6"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-calendar-6						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-calendar-7"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-calendar-7						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-calendar-8"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-calendar-8						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-bell-2"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-bell-2						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-hourglass-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-hourglass-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-next"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-next						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-chat-2"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-chat-2						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-correct"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-correct						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-photo-camera"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-photo-camera						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-fast-next"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-fast-next						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-fast-back"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-fast-back						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-down"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-down						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-back"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-back						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-up"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-up						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-arrow-down"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-arrow-down						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-arrow-up"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-arrow-up						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-accept"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-accept						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-sort"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-sort						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-arrow"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-arrow						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-back-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-back-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-add-square"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-add-square						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-quotation-mark"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-quotation-mark						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-clip-symbol"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-clip-symbol						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-check-mark"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-check-mark						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-folder"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-folder						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-cancel-music"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-cancel-music						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-cross"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-cross						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-pen"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-pen						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-email"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-email						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-graph-2"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-graph-2						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-open-box"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-open-box						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-files-and-folders"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-files-and-folders						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-ui"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-ui						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-sheet"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-sheet						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-dashboard"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-dashboard						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-user-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-user-1						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-group"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-group						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-black-back-closed-envelope-shape"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-black-back-closed-envelope-shape						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-left-arrow"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-left-arrow						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-sort-alphabetically"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-sort-alphabetically						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-sort-down"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-sort-down						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-rubbish-bin"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-rubbish-bin						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-rubbish-bin-delete-button"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-rubbish-bin-delete-button						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-calendar-9"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-calendar-9						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-tag"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-tag						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-refresh-button"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-refresh-button						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-refresh-arrow"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-refresh-arrow						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-reload-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-reload-1						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-refresh-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-refresh-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-left-arrow-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-left-arrow-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-reply"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-reply						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-reply-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-reply-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-printer"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-printer						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-print"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-print						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-shrink"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-shrink						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-resize"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-resize						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-arrow-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-arrow-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-size"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-size						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-arrow-2"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-arrow-2						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-cancel"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-cancel						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-exclamation"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-exclamation						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-line"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-line						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-warning"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-warning						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-warning-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-warning-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-layers-2"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-layers-2						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-file-2"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-file-2						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-bell-3"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-bell-3						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-bell-4"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-bell-4						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-bell-5"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-bell-5						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-bell-alarm-symbol"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-bell-alarm-symbol						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-world"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-world						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-graphic-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-graphic-1						</div>
                    </div>
                </div></div><div class="row"><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-send-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-send-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-location"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-location						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-pin-1"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-pin-1						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-start-up"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-start-up						</div>
                    </div>
                </div><div class="col-md-2">
                    <div class="kt-demo-icon">
                        <div class="kt-demo-icon__preview">
                            <i class="flaticon2-right-arrow"></i>
                        </div>
                        <div class="kt-demo-icon__class">
                            flaticon2-right-arrow						</div>
                    </div>
                </div></div>					</div>
            </div>
            <!--end::Portlet-->
        </div>
    </div>
</div>


