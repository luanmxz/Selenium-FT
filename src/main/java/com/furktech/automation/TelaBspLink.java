package com.furktech.automation;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.furktech.model.Solicitacao;

public class TelaBspLink {

        static final Logger logger = LoggerFactory.getLogger(TelaBspLink.class);

        public static void extraiBilhetes(WebDriver webdriver, WebDriverWait wait, WebElement tr,
                        List<Solicitacao> solicitacoes) {

                wait.until(ExpectedConditions
                                .elementToBeClickable(
                                                By.xpath("/html/body/bspl-root/bspl-core/div/bspl-menu/snav/ul/li[3]")));

                // ITERAR AS SOLICITACOES PRESENTES NO ENTRY PASSANDO O NUM BILHETE DE CADA UMA
                // AO SCRIPT DO CLÓVIS
                JavascriptExecutor js = (JavascriptExecutor) webdriver;
                /*
                 * js.
                 * executeScript("var bspUrl = \"https://gateway-dmz.newbsplink.iata.org\";\n" +
                 * //
                 * "// var destinationUrl = \"http://localhost:8080\";\n" + //
                 * "var destinationUrl = \"https://beta-re3ve-api.furk.tech\";\n" + //
                 * "var bspId = \"6682\";\n" + //
                 * "var token = \"eyJkb21haW4iOiJjdmMiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjdmNAZnVyay50ZWNoIiwiaWF0IjoxNzE2NDA0ODk4LCJleHAiOjE3MTY0MzM2OTh9.BLXr9v1F8jyjUe7L3LLlkS2z7Pl4Y-1WBOjlhcH4KFM\";\n"
                 * + //
                 * "var destinationUser = \"cvc@furk.tech\";\n" + //
                 * "var destinationPassword = \"@re3veHom@.\";\n" + //
                 * " \n" + //
                 * "var totalRecords = 0;\n" + //
                 * "var currentPage = 0;\n" + //
                 * "var pageSize = 50;\n" + //
                 * "var delayPerRequest = 700;\n" + //
                 * "var iterations = 0;\n" + //
                 * "var maxIterations = 50;\n" + //
                 * "var dataIni = \"2024-06-01\";\n" + //
                 * "var dataFim = \"2024-06-08\";\n" + //
                 * "//var transactionCodes = \"EMDS\";\n" + //
                 * "//var transactionCodes = \"ADMA::ADMD\";\n" + //
                 * "//var transactionCodes = \"SPCR::SPDR\";\n" + //
                 * "var transactionCodes = \"RFND\";\n" + //
                 * " \n" + //
                 * " \n" + //
                 * "//https://gateway-dmz.newbsplink.iata.org/api/globalsearch/billing-analysis?page=0&size=20&filters=billingAnalysisEndDate::gte::2024-05-31,billingAnalysisEndDate::lte::2024-05-31,transactionCode::in::EMDS,isoCountryCode::eq::BR\n"
                 * + //
                 * "//https://gateway-dmz.newbsplink.iata.org/api/globalsearch/billing-analysis?page=0&size=20&filters=billingAnalysisEndDate::gte::2024-05-31,billingAnalysisEndDate::lte::2024-05-31,transactionCode::in::EMDS::EMDA,isoCountryCode::eq::BR\n"
                 * + //
                 * "//busca token\n" + //
                 * "fetch(destinationUrl + '/api/v1/auth/login', {\n" + //
                 * "    method: 'POST',\n" + //
                 * "    headers: {\n" + //
                 * "        'Content-Type': 'application/json'\n" + //
                 * "    },\n" + //
                 * "    body: JSON.stringify({\n" + //
                 * "        \"email\": destinationUser,\n" + //
                 * "        \"password\": destinationPassword\n" + //
                 * "    })\n" + //
                 * "})\n" + //
                 * "    .then(response => response.json())\n" + //
                 * "    .then(data => {\n" + //
                 * "        token = data.token;\n" + //
                 * "        if (token != null) {\n" + //
                 * "            fetch(bspUrl + '/api/globalsearch/billing-analysis?page=' + currentPage + '&size=' + pageSize + '&filters=isoCountryCode::eq::BR,transactionCode::in::'+transactionCodes+',billingAnalysisEndDate::gte::' + dataIni + ',billingAnalysisEndDate::lte::' + dataFim, {\n"
                 * + //
                 * "                method: 'GET',\n" + //
                 * "                credentials: 'include'\n" + //
                 * "            })\n" + //
                 * "                .then(response => response.json())\n" + //
                 * "                .then(data => {\n" + //
                 * "                    totalRecords = data.total;\n" + //
                 * "                    console.log('Total de documentos: ' + totalRecords);\n"
                 * + //
                 * " \n" + //
                 * "                    for (currentPage = 0; currentPage < Math.ceil(totalRecords / pageSize); currentPage++) {\n"
                 * + //
                 * "                        //busca lista de items\n" + //
                 * "                        let endpointCall = bspUrl + \"/api/globalsearch/billing-analysis?page=\" + encodeURIComponent(currentPage) + \"&size=\" + encodeURIComponent(pageSize) + \"&filters=\" + encodeURIComponent(\"isoCountryCode::eq::BR,transactionCode::in::\"+transactionCodes+\",billingAnalysisEndDate::gte::\" + dataIni + \",billingAnalysisEndDate::lte::\" + dataFim);\n"
                 * + //
                 * "                        //console.log('Calling endpoint: ' + endpointCall);\n"
                 * + //
                 * "                        setTimeout(() => getPageBillingAnalysis(endpointCall), (currentPage * delayPerRequest) * pageSize);\n"
                 * + //
                 * "                        console.log(\"CurrentPage: \" + currentPage);\n" +
                 * //
                 * "                    }\n" + //
                 * "                })\n" + //
                 * "                .catch(error => console.error('Erro:', error));\n" + //
                 * "        }\n" + //
                 * "    })\n" + //
                 * "    .catch(error => console.error('Erro:', error));\n" + //
                 * " \n" + //
                 * " \n" + //
                 * "function getPageBillingAnalysis(endpointCall) {\n" + //
                 * "    console.log('Calling endpoint: ' + endpointCall);\n" + //
                 * "    fetch(endpointCall, {\n" + //
                 * "        method: 'GET',\n" + //
                 * "        credentials: 'include'\n" + //
                 * "    })\n" + //
                 * "        .then(response => response.json())\n" + //
                 * "        .then(data => {\n" + //
                 * " \n" + //
                 * "            fetch(destinationUrl + '/api/v1/bsp/capture', {\n" + //
                 * "                method: 'POST',\n" + //
                 * "                headers: {\n" + //
                 * "                    'Content-Type': 'application/json',\n" + //
                 * "                    'Authorization': 'Bearer ' + token\n" + //
                 * "                },\n" + //
                 * "                body: JSON.stringify(data)\n" + //
                 * "            })\n" + //
                 * "            .then(result => {\n" + //
                 * "                //confere se o envio funcionou\n" + //
                 * "                if (result.status === 200) {\n" + //
                 * "                    console.log('Dados enviados com sucesso:');\n" + //
                 * "                } else {\n" + //
                 * "                    result.json().then(data => {\n" + //
                 * "                        console.error('Erro ao enviar os dados. Código de status:' + result.status, data);\n"
                 * + //
                 * "                    });\n" + //
                 * "                }\n" + //
                 * "            })\n" + //
                 * "            .catch(error => console.error('Erro:', error));\n" + //
                 * " \n" + //
                 * " \n" + //
                 * "            data.records.forEach((item, index) => {\n" + //
                 * "                //console.log(\"Index: \"+ index);\n" + //
                 * "                //console.log('Delay: ' + index * delayPerRequest);\n" + //
                 * "                let itemCopy = Object.assign({}, item);\n" + //
                 * "                setTimeout(() => {\n" + //
                 * "                    getDocumentDetails(itemCopy);\n" + //
                 * "                    //console.log('Consulta Documento: ' + itemCopy.id);\n"
                 * + //
                 * "                }, index * delayPerRequest);\n" + //
                 * "            })\n" + //
                 * "        })\n" + //
                 * "        //.then(result => console.log('Dados capturados com sucesso:', result))\n"
                 * + //
                 * "        .catch(error => console.error('Erro:', error));\n" + //
                 * "}\n" + //
                 * "async function getDocumentDetails(item) {\n" + //
                 * "    if (item.id != null) {\n" + //
                 * "        //captura documento detalhado\n" + //
                 * "        endpointCall = bspUrl + '/api/document-enquiry/documents/' + item.id;\n"
                 * + //
                 * "        console.log('Calling endpoint: ' + endpointCall);\n" + //
                 * "        fetch(endpointCall, {\n" + //
                 * "            method: 'GET',\n" + //
                 * "            credentials: 'include'\n" + //
                 * "        })\n" + //
                 * "            .then(response => response.json())\n" + //
                 * "            .then(data => {\n" + //
                 * "                //enviar dados todos para gravação em nosso sistema\n" + //
                 * "                return fetch(destinationUrl + '/api/v1/bsp/capture', {\n" +
                 * //
                 * "                    method: 'POST',\n" + //
                 * "                    headers: {\n" + //
                 * "                        'Content-Type': 'application/json',\n" + //
                 * "                        'Authorization': 'Bearer ' + token\n" + //
                 * "                    },\n" + //
                 * "                    body: JSON.stringify(data)\n" + //
                 * "                })\n" + //
                 * "                    .then(result => {\n" + //
                 * "                        //confere se o envio funcionou\n" + //
                 * "                        if (result.status === 200) {\n" + //
                 * "                            console.log('Dados enviados com sucesso:', item.id);\n"
                 * + //
                 * "                        } else {\n" + //
                 * "                            result.json().then(data => {\n" + //
                 * "                                console.error('Erro ao enviar os dados. Código de status:' + result.status, data);\n"
                 * + //
                 * "                            });\n" + //
                 * "                        }\n" + //
                 * "                    })\n" + //
                 * "                    .catch(error => console.error('Erro:', error));\n" + //
                 * "            })\n" + //
                 * "            //.then(response => console.log('Dados de detalhe capturados com sucesso:', response))\n"
                 * + //
                 * "            .catch(error => console.error('Erro:', error));\n" + //
                 * "    }\n" + //
                 * "    else {\n" + //
                 * "        console.log('Documento não encontrado:', item);\n" + //
                 * "    }\n" + //
                 * "}");
                 * 
                 */

        }
}
