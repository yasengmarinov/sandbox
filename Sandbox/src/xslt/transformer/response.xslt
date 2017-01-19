<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/DecisionResponse">
        <html>
            <head>
                <style type="text/css">
                    body,td {font-family: Arial, Helvetica, sans-serif;	font-size: 12px;	color: #000000;}
                    th {font-family: Arial, Helvetica, sans-serif; font-size: 12px; font-weight: bold; color: #000000;}
                    .highlight1 {font-family: Arial, Helvetica, sans-serif; font-size: 16px; font-weight: bold; color: #000000;}
                    .bold {font-family: Arial, Helvetica, sans-serif;	font-size: 12px;	font-weight: bold; color: #000000;}
                    .heading1{font-family: Arial, Helvetica, sans-serif;	font-size: 14px;	font-weight: bold; color: #000000;}
                    .small {font-family: Arial, Helvetica, sans-serif; font-size: 11px; color: #000000;}
                    .smaller {font-family: Arial, Helvetica, sans-serif; font-size: 10px; color: #000000;}
                    .italic {font-family: Arial, Helvetica, sans-serif; font-size: 12px; font-weight: bold; font-style: italic; color: #2222B5;}
                    .bigHeading {font-family: Arial, Helvetica, sans-serif; font-size: 24px; font-weight: bold; color: #000000;}
                    a.smallLink { text-decoration: none; font-family: Arial, Helvetica, sans-serif; font-size: 9px; color: #2222B5;}
                    .reallysmall {font-family: Arial, Helvetica, sans-serif; font-size: 9px; color: #000000;}
                    table.withborder { border: outset 1pt; border-collapse: collapse; border-spacing: 1pt; border: 2px solid black; }
                    td.withborder { border: inset 1pt; border: 1px solid black; font-size: 11px; padding: 3px,5px; }
                    th.withborder { border: inset 1pt; border: 1px solid black;}
                    td.boldwithborder { border: inset 1pt; border: 1px solid black; font-size: 12px; font-weight: bold; padding: 3px,5px; }
                </style>
                <xsl:variable name="lowerCase">abcdefghijklmnopqrstuvwxyz</xsl:variable>
                <xsl:variable name="upperCase">ABCDEFGHIJKLMNOPQRSTUVWXYZ</xsl:variable>


            </head>
            <body vlink="3333CC" alink="3333CC" link="3333CC">
                <table border="0" width="640">
                    <tr>
                        <td colspan="2" align="center">
                            <div class="bigHeading">Decision Response</div>
                        </td>
                    </tr>
                    <xsl:variable name="url">/USS/TransactionHistoryDetail.do?dsTransactionId=<xsl:value-of select="DSTransactionID"/>&amp;customerRequestId=<xsl:value-of select="CustomerRequestID"/>{securitytoken}</xsl:variable>


                    <tr>
                        <td colspan="2" height="30"><xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text></td>
                    </tr>
                    <tr>
                        <td align="right" colspan="2">
                            <a target="_new"><xsl:attribute name="href"><xsl:value-of select="$url"/></xsl:attribute>
                                <xsl:choose>
                                    <xsl:when test = "CustomerId != '51'">
                                        uRetrieve
                                    </xsl:when>
                                    <xsl:otherwise>
                                        uRecoverr
                                        <xsl:value-of select="CustomerId" />
                                    </xsl:otherwise>
                                </xsl:choose>
                            </a>

                            <xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text>
                            <xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text>
                            <xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text>
                            <xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text>
                            <a href="/uProcess/DisplayFormPage.do?clear" vlink="1111FF">New Application</a>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2"> <br/> </td>
                    </tr>
                    <xsl:for-each select="Decisions/Decision">
                        <xsl:if test="position() = last() or '1' != '2'">
                            <tr>
                                <td class="bold" width="100">Decision Name:</td><td width="430"><xsl:value-of select="Name"/></td>
                            </tr>
                            <tr>
                                <td colspan="2" height="10"></td>
                            </tr>
                            <tr>
                                <td class="bold" nowrap="nowrap" valign="top">Decision Description:</td><td><xsl:value-of select="Description"/></td>
                            </tr>
                            <tr>
                                <td colspan="2" height="10"></td>
                            </tr>

                            <xsl:choose>
                                <xsl:when test="Limit != '-999.999'">
                                    <tr>
                                        <td class="bold">Limit:</td><td><xsl:value-of select="Limit"/></td>
                                    </tr>
                                </xsl:when>
                            </xsl:choose>

                            <xsl:choose>
                                <xsl:when test="Deposit != '-999.999'">
                                    <tr>
                                        <td class="bold">Deposit:</td><td><xsl:value-of select="Deposit"/></td>
                                    </tr>
                                </xsl:when>
                            </xsl:choose>

                            <tr>
                                <td colspan="2" height="10"></td>
                            </tr>
                            <xsl:for-each select="SelectedFields/Field">
                                <xsl:choose>
                                    <xsl:when test="Value != '-999.999'">
                                        <tr>
                                            <td class="bold"><xsl:value-of select="Name"/>:</td><td><xsl:value-of select="Value"/></td>
                                        </tr>
                                    </xsl:when>
                                </xsl:choose>
                            </xsl:for-each>
                            <xsl:choose>
                                <xsl:when test="position() != last()">
                                    <tr>
                                        <td colspan="2"><hr size="1" color="#000000" align="left" width="656"/></td>
                                    </tr>
                                </xsl:when>
                            </xsl:choose>
                        </xsl:if>
                    </xsl:for-each>
                    <!-- Code Added for Deposit Calculator starts here -->
                    <FORM name="customerDepositCalculator" action="" method="post">
                        <xsl:for-each select="DerivedVariables/DerivedVariable">
                            <xsl:choose>
                                <xsl:when test="contains(Name, 'Customer Type')">

                                    <xsl:variable name="appType">
                                        <xsl:value-of select="Value"/>
                                    </xsl:variable>

                                    <xsl:if test="$appType != ''">
                                        <input name="custType" type="hidden">
                                            <xsl:attribute name='value'>
                                                <xsl:value-of select='$appType' />
                                            </xsl:attribute>
                                        </input>
                                    </xsl:if>
                                </xsl:when>
                            </xsl:choose>
                        </xsl:for-each>

                        <xsl:for-each select="DerivedVariables/DerivedVariable">
                            <xsl:choose>


                                <xsl:when test="contains(Name, 'Local Access Checked')">
                                    <xsl:variable name="localAccess">
                                        <xsl:value-of select="Value"/>
                                    </xsl:variable>

                                    <xsl:if test="$localAccess != ''">
                                        <input name="localAccessChecked" type="hidden">
                                            <xsl:attribute name='value'>
                                                <xsl:value-of select='$localAccess' />
                                            </xsl:attribute>
                                        </input>
                                    </xsl:if>
                                </xsl:when>

                                <xsl:when test="Name = 'DC-High Speed Checked'">
                                    <xsl:variable name="highSpeed">
                                        <xsl:value-of select="Value"/>
                                    </xsl:variable>

                                    <xsl:if test="$highSpeed != ''">
                                        <input name="highSpeedChecked" type="hidden">
                                            <xsl:attribute name='value'>
                                                <xsl:value-of select='$highSpeed' />
                                            </xsl:attribute>
                                        </input>
                                    </xsl:if>
                                </xsl:when>
                                <xsl:when test="Name = 'DC-High Speed Bundle Checked'">
                                    <xsl:variable name="highSpeedBundle">
                                        <xsl:value-of select="Value"/>
                                    </xsl:variable>

                                    <xsl:if test="$highSpeedBundle != ''">
                                        <input name="highSpeedBundleChecked" type="hidden">
                                            <xsl:attribute name='value'>
                                                <xsl:value-of select='$highSpeedBundle' />
                                            </xsl:attribute>
                                        </input>

                                    </xsl:if>
                                </xsl:when>
                                <xsl:when test="Name = 'DC-Max Checked'">
                                    <xsl:variable name="max">
                                        <xsl:value-of select="Value"/>
                                    </xsl:variable>

                                    <xsl:if test="$max != ''">
                                        <input name="maxChecked" type="hidden">
                                            <xsl:attribute name='value'>
                                                <xsl:value-of select='$max' />
                                            </xsl:attribute>
                                        </input>

                                    </xsl:if>
                                </xsl:when>
                                <xsl:when test="Name = 'DC-Max Bundle Checked'">
                                    <xsl:variable name="maxBundle">
                                        <xsl:value-of select="Value"/>
                                    </xsl:variable>

                                    <xsl:if test="$maxBundle != ''">
                                        <input name="maxBundleChecked" type="hidden">
                                            <xsl:attribute name='value'>
                                                <xsl:value-of select='$maxBundle' />
                                            </xsl:attribute>
                                        </input>

                                    </xsl:if>
                                </xsl:when>
                                <xsl:when test="contains(Name, 'Toll Discount Plan')">
                                    <xsl:variable name="toll">
                                        <xsl:value-of select="Value"/>
                                    </xsl:variable>

                                    <xsl:if test="$toll != ''">
                                        <input name="tollChecked" type="hidden">
                                            <xsl:attribute name='value'>
                                                <xsl:value-of select='$toll' />
                                            </xsl:attribute>
                                        </input>

                                    </xsl:if>
                                </xsl:when>


                                <xsl:when test="Name = 'DC-Credit Class'">
                                    <xsl:variable name="creditClass" select="Value"/>


                                    <xsl:if test="$creditClass != ''">
                                        <input name="creditClass" type="hidden">
                                            <xsl:attribute name='value'>
                                                <xsl:value-of select='$creditClass' />
                                            </xsl:attribute>
                                        </input>
                                    </xsl:if>
                                </xsl:when>

                                <xsl:when test="contains(Name, 'Show Calculator')">
                                    <xsl:if test="contains(Value, 'Y')">
                                        <tr><td colspan="2"><br /> <br /></td></tr>
                                        <tr>
                                            <td class="bigHeading" colspan="2" align="center"> Customer Deposit Calculator</td>
                                        </tr>
                                        <tr><td colspan="2"><br /> <br /></td></tr>
                                        <tr>
                                            <td colspan="2">
                                                <TABLE width="300" border="0">
                                                    <TBODY>

                                                        <tr>
                                                            <td class="bold" colspan="2">Customer Credit Class </td>
                                                            <td class="bold">Amount</td></tr>
                                                        <TR>
                                                            <TD>Local Acccess </TD>
                                                            <TD><INPUT onclick="reEvaluateForm('false','false','false','false');" type="checkbox" name="localAccess" onload ="call();" /></TD>
                                                            <TD><INPUT name="localAccessAmmount" /> </TD></TR>
                                                        <TR>
                                                            <TD>Internet </TD>
                                                            <TD><INPUT
                                                                    onclick="reEvaluateForm('true','false','false','false');"
                                                                    type="checkbox" name="highSpeed" /></TD>
                                                            <TD><INPUT name="highSpeedAmmount" /></TD></TR>
                                                        <TR>
                                                            <TD>Internet Bundle </TD>
                                                            <TD><INPUT
                                                                    onclick="reEvaluateForm('false','true','false','false');"
                                                                    type="checkbox" name="highSpeedBundle" /></TD>
                                                            <TD><INPUT name="highSpeedBundleAmmount" /></TD></TR>
                                                        <TR>
                                                            <TD>Max</TD>
                                                            <TD><INPUT
                                                                    onclick="reEvaluateForm('false','false','true','false');"
                                                                    type="checkbox" name="maxx" /></TD>
                                                            <TD><INPUT name="maxAmmount" /></TD></TR>
                                                        <TR>
                                                            <TD>Max Bundle </TD>
                                                            <TD><INPUT
                                                                    onclick="reEvaluateForm('false','false','false','true');"
                                                                    type="checkbox" name="maxBundle" /></TD>
                                                            <TD><INPUT name="maxBundleAmmount" /></TD></TR>
                                                        <TR>
                                                            <TD>Toll/Toll Discount Plan </TD>
                                                            <TD><INPUT onclick="reEvaluateForm();" type="checkbox"
                                                                       name="tollTollDiscountPlan" /> </TD>
                                                            <TD><INPUT name="tollTollDiscountPlanAmmount" /></TD></TR>
                                                        <TR>
                                                            <TD>Total Deposit Required: </TD>
                                                            <td></td>
                                                            <TD><INPUT name="totalDepositRequired" />
                                                            </TD>
                                                        </TR>
                                                    </TBODY>
                                                </TABLE>


                                                <SCRIPT type="text/javascript">
																																<xsl:text disable-output-escaping="yes"><![CDATA[
<!--

																																	var customerType="";

																																	function reEvaluateForm(hs,hsb,mx,mxb){

																																							// Constant Variables
																																							var CIBSlocalAccess = 25;
																																							var CIBShighSpeed = 35;
																																							var CIBShighSpeedBundle = 70;
																																							var CIBSmaxx = 50;
																																							var CIBSmaxBundle = 90;
																																							var CIBStollTollDiscountPlan = 35;

																																							var EIBSlocalAccess = 40;
																																							var EIBShighSpeed = 35;
																																							var EIBShighSpeedBundle = 0;
																																							var EIBSmaxx = 100;
																																							var EIBSmaxBundle = 0;
																																							var EIBStollTollDiscountPlan = 50;

																																							var creditMultiplierD = 2;
																																							var creditMultiplierE = 3;
																																							var creditMultiplier = 0;

																																							// Working Variable Initialization
																																							var total = 0;
																																							var localAccess = 0;
																																							var highSpeed = 0;
																																							var highSpeedBundle = 0;
																																							var maxx = 0;
																																							var maxBundle = 0;
																																							var tollTollDiscountPlan = 0;


																																							var customerGroup ='CIBS';
																																							var customerCreditClass = 'D';

																																							if (hsb == "true" && document.forms.customerDepositCalculator.highSpeedBundle.checked)
																																								{
																																									document.forms.customerDepositCalculator.highSpeedBundle.checked = true;
																																									document.forms.customerDepositCalculator.highSpeed.checked = false;
																																									document.forms.customerDepositCalculator.maxBundle.checked = false;
																																									document.forms.customerDepositCalculator.maxx.checked = false;
																																									document.forms.customerDepositCalculator.tollTollDiscountPlan.checked = false;
																																								}

																																							if (hs == "true" && document.forms.customerDepositCalculator.highSpeed.checked)
																																								{
																																									document.forms.customerDepositCalculator.highSpeed.checked = true;
																																								  document.forms.customerDepositCalculator.highSpeedBundle.checked = false;
																																								  document.forms.customerDepositCalculator.maxBundle.checked = false;
																																									document.forms.customerDepositCalculator.maxx.checked = false;
																																									document.forms.customerDepositCalculator.tollTollDiscountPlan.checked = false;
																																								}

																																							if (mxb == "true" && document.forms.customerDepositCalculator.maxBundle.checked)
																																							{
																																								document.forms.customerDepositCalculator.maxBundle.checked = true;
																																								document.forms.customerDepositCalculator.maxx.checked = false;
																																								document.forms.customerDepositCalculator.highSpeed.checked = false;
																																							  document.forms.customerDepositCalculator.highSpeedBundle.checked = false;
																																							  document.forms.customerDepositCalculator.tollTollDiscountPlan.checked = false;
																																							}
																																							if (mx == "true" && document.forms.customerDepositCalculator.maxx.checked)
																																							{
																																								document.forms.customerDepositCalculator.maxx.checked = true;
																																								document.forms.customerDepositCalculator.maxBundle.checked = false;
																																								document.forms.customerDepositCalculator.highSpeed.checked = false;
																																								document.forms.customerDepositCalculator.highSpeedBundle.checked = false;
																																								document.forms.customerDepositCalculator.tollTollDiscountPlan.checked = false;
																																							}

																																							if(document.forms.customerDepositCalculator.tollTollDiscountPlan.checked)
																																							{
																																								document.forms.customerDepositCalculator.maxx.checked = false;
																																								document.forms.customerDepositCalculator.maxBundle.checked = false;
																																								document.forms.customerDepositCalculator.highSpeed.checked = false;
																																								document.forms.customerDepositCalculator.highSpeedBundle.checked = false;
																																								document.forms.customerDepositCalculator.tollTollDiscountPlan.checked = true;

																																							}

																																							customerGroup = customerType;
																																							customerCreditClass =document.customerDepositCalculator.creditClass.value;

																																							if (customerCreditClass=='D') { creditMultiplier = creditMultiplierD}
																																							if (customerCreditClass=='E') { creditMultiplier = creditMultiplierE}

																																							if (customerGroup=='CIBS')
																																							{
																																								if (document.forms.customerDepositCalculator.localAccess.checked) { document.forms.customerDepositCalculator.localAccessAmmount.value = '$' + CIBSlocalAccess; localAccess = CIBSlocalAccess;} else { document.forms.customerDepositCalculator.localAccessAmmount.value = ''; }
																																								if (document.forms.customerDepositCalculator.highSpeed.checked) { document.forms.customerDepositCalculator.highSpeedAmmount.value = '$' + CIBShighSpeed; highSpeed = CIBShighSpeed;}  else  { document.forms.customerDepositCalculator.highSpeedAmmount.value = ''; }
																																								if (document.forms.customerDepositCalculator.highSpeedBundle.checked) { document.forms.customerDepositCalculator.highSpeedBundleAmmount.value = '$' + CIBShighSpeedBundle; highSpeedBundle = CIBShighSpeedBundle;} else { document.forms.customerDepositCalculator.highSpeedBundleAmmount.value = ''; }
																																								if (document.forms.customerDepositCalculator.maxx.checked) { document.forms.customerDepositCalculator.maxAmmount.value = '$' + CIBSmaxx; maxx = CIBSmaxx;} else { document.forms.customerDepositCalculator.maxAmmount.value = ''; }
																																								if (document.forms.customerDepositCalculator.maxBundle.checked) { document.forms.customerDepositCalculator.maxBundleAmmount.value = '$' + CIBSmaxBundle; maxBundle = CIBSmaxBundle;} else  { document.forms.customerDepositCalculator.maxBundleAmmount.value = ''; }
																																								if (document.forms.customerDepositCalculator.tollTollDiscountPlan.checked) { document.forms.customerDepositCalculator.tollTollDiscountPlanAmmount.value = '$' + CIBStollTollDiscountPlan; tollTollDiscountPlan = CIBStollTollDiscountPlan;} else { document.forms.customerDepositCalculator.tollTollDiscountPlanAmmount.value = ''; }
																																							}

																																							if (customerGroup=='EIBS')
																																							{
																																								if (document.forms.customerDepositCalculator.localAccess.checked) { document.forms.customerDepositCalculator.localAccessAmmount.value = '$' + EIBSlocalAccess; localAccess = EIBSlocalAccess;} else { document.forms.customerDepositCalculator.localAccessAmmount.value = ''; }
																																								if (document.forms.customerDepositCalculator.highSpeed.checked) { document.forms.customerDepositCalculator.highSpeedAmmount.value = '$' + EIBShighSpeed; highSpeed = EIBShighSpeed;}  else  { document.forms.customerDepositCalculator.highSpeedAmmount.value = ''; }
																																								if (document.forms.customerDepositCalculator.highSpeedBundle.checked) { document.forms.customerDepositCalculator.highSpeedBundleAmmount.value = 'NA'; highSpeedBundle = EIBShighSpeedBundle;} else { document.forms.customerDepositCalculator.highSpeedBundleAmmount.value = ''; }
																																								if (document.forms.customerDepositCalculator.maxx.checked) { document.forms.customerDepositCalculator.maxAmmount.value = '$' + EIBSmaxx; maxx = EIBSmaxx;} else { document.forms.customerDepositCalculator.maxAmmount.value = ''; }
																																								if (document.forms.customerDepositCalculator.maxBundle.checked) { document.forms.customerDepositCalculator.maxBundleAmmount.value = 'NA'; maxBundle = EIBSmaxBundle;} else  { document.forms.customerDepositCalculator.maxBundleAmmount.value = ''; }
																																								if (document.forms.customerDepositCalculator.tollTollDiscountPlan.checked) { document.forms.customerDepositCalculator.tollTollDiscountPlanAmmount.value = '$' + EIBStollTollDiscountPlan; tollTollDiscountPlan = EIBStollTollDiscountPlan;} else { document.forms.customerDepositCalculator.tollTollDiscountPlanAmmount.value = ''; }
																																							}

																																							total = localAccess + highSpeed + highSpeedBundle + maxx + maxBundle + tollTollDiscountPlan;
																																							document.forms.customerDepositCalculator.totalDepositRequired.value='$'+(total*creditMultiplier);

																																							}

																																							function defaultFill()
																																							{

																																					/*		alert(" Local Access: " + document.customerDepositCalculator.localAccessChecked.value);
																																							alert(" High Speed: "+ document.customerDepositCalculator.highSpeedChecked.value);
																																							alert(" High Speed Bundle: "+ document.customerDepositCalculator.highSpeedBundleChecked.value);
																																							alert(" Max: "+ document.customerDepositCalculator.maxChecked.value);
																																							alert(" Max Bundle: "+ document.customerDepositCalculator.maxBundleChecked.value);
																																							alert(" Toll: "+ document.customerDepositCalculator.tollChecked.value);
																																						*/
																																								if(document.customerDepositCalculator.custType.value == "1" || document.customerDepositCalculator.custType.value == "1.0")
																																								{
																																									customerType = "CIBS";
																																								}
																																								else
																																								{
																																									customerType = "EIBS";
																																								}

																																								document.forms.customerDepositCalculator.localAccessAmmount.disabled = true;
																																								document.forms.customerDepositCalculator.highSpeedAmmount.disabled = true;
																																								document.forms.customerDepositCalculator.highSpeedBundleAmmount.disabled = true;
																																								document.forms.customerDepositCalculator.maxAmmount.disabled = true;
																																								document.forms.customerDepositCalculator.maxBundleAmmount.disabled = true;
																																								document.forms.customerDepositCalculator.tollTollDiscountPlanAmmount.disabled = true;
																																								document.forms.customerDepositCalculator.totalDepositRequired.disabled = true;

																																							if(document.customerDepositCalculator.localAccessChecked.value == 'Yes' || document.customerDepositCalculator.localAccessChecked.value == 'on')
																																									document.customerDepositCalculator.localAccess.checked = true;

																																								if(document.customerDepositCalculator.highSpeedChecked.value == 'Yes' || document.customerDepositCalculator.highSpeedChecked.value == 'on')
																																									document.customerDepositCalculator.highSpeed.checked = true;

																																								if(document.customerDepositCalculator.highSpeedBundleChecked.value == 'Yes' || document.customerDepositCalculator.highSpeedBundleChecked.value == 'on')
																																									document.customerDepositCalculator.highSpeedBundle.checked = true;

																																								if(document.customerDepositCalculator.maxChecked.value == 'Yes' || document.customerDepositCalculator.maxChecked.value == 'on')
																																									document.customerDepositCalculator.maxx.checked = true;

																																								if(document.customerDepositCalculator.maxBundleChecked.value == 'Yes' || document.customerDepositCalculator.maxBundleChecked.value == 'on')
																																									document.customerDepositCalculator.maxBundle.checked = true;

																																								if(document.customerDepositCalculator.tollChecked.value == 'Yes' || document.customerDepositCalculator.tollChecked.value == 'on')
																																									document.customerDepositCalculator.tollTollDiscountPlan.checked = true;



																																							reEvaluateForm(document.customerDepositCalculator.highSpeed.checked,document.customerDepositCalculator.highSpeedBundle.checked,document.customerDepositCalculator.maxx.checked,document.customerDepositCalculator.tollChecked.value);

																																							}

																			defaultFill();
										//-->
]]></xsl:text>


                                                </SCRIPT>

                                            </td>
                                        </tr>
                                    </xsl:if>
                                </xsl:when>
                            </xsl:choose>

                        </xsl:for-each>
                    </FORM>




                    <xsl:choose>
                        <xsl:when test="Decisions/Decision"></xsl:when>
                        <xsl:otherwise>
                            <tr>
                                <td colspan="2">There is no decision for this application.</td>
                            </tr>
                        </xsl:otherwise>
                    </xsl:choose>
                    <xsl:choose>
                        <xsl:when test="Error">
                            <tr>
                                <td colspan="2" height="15"></td>
                            </tr>
                            <tr>
                                <td colspan="2">The system has encountered the following error:</td>
                            </tr>
                            <tr>
                                <td colspan="2" height="7"></td>
                            </tr>
                            <tr>
                                <td class="bold">Type:</td><td><xsl:value-of select="Error/Type"/></td>
                            </tr>
                            <tr>
                                <td class="bold">Message:</td><td><xsl:value-of select="Error/Message"/></td>
                            </tr>
                            <tr>
                                <td class="bold">Description:</td><td><xsl:value-of select="Error/Description"/></td>
                            </tr>
                        </xsl:when>
                    </xsl:choose>
                </table>
            </body>
        </html>
    </xsl:template>
    <xsl:template name="format-date">
        <xsl:param name="date"/>
        <xsl:variable name="year" select="substring($date,1,4)"/>
        <xsl:variable name="month" select="substring($date,6,2)"/>
        <xsl:variable name="day" select="substring($date,9,2)"/>
        <xsl:variable name="newMonth" select="substring(substring-after('01Jan02Feb03Mar04Apr05May06Jun07Jul08Aug09Sep10Oct11Nov12Dec', $month), 1, 3)"/>
        <xsl:choose>
            <xsl:when test="string-length($date) = 0">
                N / A
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="concat($newMonth, ' ', $day, ', ', $year)"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="removeLeadingZeros">
        <xsl:param name="num"/>
        <xsl:message>
            <xsl:value-of select="$num"/>
        </xsl:message>
        <xsl:choose>
            <xsl:when test="starts-with($num,'0')">
                <xsl:call-template name="removeLeadingZeros">
                    <xsl:with-param name="num">
                        <xsl:value-of select="substring-after($num,'0' )"/>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="$num"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="createCipiList">
        <xsl:param name="currentList"/>
        <xsl:for-each select="/EfxTransmit/EfxReport/CNCommercialCreditReports/CNCommercialCreditReport/CNCIPI/CIPIQuarters/CIPIQuarter">
            <xsl:value-of select="concat(concat(concat(concat($currentList, PaymentIndex), '|'), CompositeIndex),'#')"/>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>