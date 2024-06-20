var bspUrl = 'https://gateway-dmz.newbsplink.iata.org';

// var destinationUrl = "http://localhost:8080";

var destinationUrl = 'https://beta-re3ve-api.furk.tech';

var bspId = '6682';

var token =
	'eyJkb21haW4iOiJjdmMiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjdmNAZnVyay50ZWNoIiwiaWF0IjoxNzE2NDA0ODk4LCJleHAiOjE3MTY0MzM2OTh9.BLXr9v1F8jyjUe7L3LLlkS2z7Pl4Y-1WBOjlhcH4KFM';

var destinationUser = 'cvc@furk.tech';

var destinationPassword = '@re3veHom@.';

var totalRecords = 0;

var currentPage = 0;

var pageSize = 50;

var delayPerRequest = 700;

var iterations = 0;

var maxIterations = 50;

var dataIni = '2024-06-01';

var dataFim = '2024-06-08';

//var transactionCodes = "EMDS";

//var transactionCodes = "ADMA::ADMD";

//var transactionCodes = "SPCR::SPDR";

var transactionCodes = 'RFND';

//[https://gateway-dmz.newbsplink.iata.org/api/globalsearch/billing-analysis?page=0&size=20&filters=billingAnalysisEndDate::gte::2024-05-31,billingAnalysisEndDate::lte::2024-05-31,transactionCode::in::EMDS,isoCountryCode::eq::BR](https://gateway-dmz.newbsplink.iata.org/api/globalsearch/billing-analysis?page=0&size=20&filters=billingAnalysisEndDate::gte::2024-05-31,billingAnalysisEndDate::lte::2024-05-31,transactionCode::in::EMDS,isoCountryCode::eq::BR "https://gateway-dmz.newbsplink.iata.org/api/globalsearch/billing-analysis?page=0&size=20&filters=billinganalysisenddate::gte::2024-05-31,billinganalysisenddate::lte::2024-05-31,transactioncode::in::emds,isocountrycode::eq::br")

//[https://gateway-dmz.newbsplink.iata.org/api/globalsearch/billing-analysis?page=0&size=20&filters=billingAnalysisEndDate::gte::2024-05-31,billingAnalysisEndDate::lte::2024-05-31,transactionCode::in::EMDS::EMDA,isoCountryCode::eq::BR](https://gateway-dmz.newbsplink.iata.org/api/globalsearch/billing-analysis?page=0&size=20&filters=billingAnalysisEndDate::gte::2024-05-31,billingAnalysisEndDate::lte::2024-05-31,transactionCode::in::EMDS::EMDA,isoCountryCode::eq::BR "https://gateway-dmz.newbsplink.iata.org/api/globalsearch/billing-analysis?page=0&size=20&filters=billinganalysisenddate::gte::2024-05-31,billinganalysisenddate::lte::2024-05-31,transactioncode::in::emds::emda,isocountrycode::eq::br")

//busca token

fetch(destinationUrl + '/api/v1/auth/login', {
	method: 'POST',

	headers: {
		'Content-Type': 'application/json',
	},

	body: JSON.stringify({
		email: destinationUser,

		password: destinationPassword,
	}),
})
	.then((response) => response.json())

	.then((data) => {
		token = data.token;

		if (token != null) {
			fetch(
				bspUrl +
					'/api/globalsearch/billing-analysis?page=' +
					currentPage +
					'&size=' +
					pageSize +
					'&filters=isoCountryCode::eq::BR,transactionCode::in::' +
					transactionCodes +
					',billingAnalysisEndDate::gte::' +
					dataIni +
					',billingAnalysisEndDate::lte::' +
					dataFim,
				{
					method: 'GET',

					credentials: 'include',
				}
			)
				.then((response) => response.json())

				.then((data) => {
					totalRecords = data.total;

					console.log('Total de documentos: ' + totalRecords);

					for (
						currentPage = 0;
						currentPage < Math.ceil(totalRecords / pageSize);
						currentPage++
					) {
						//busca lista de items

						let endpointCall =
							bspUrl +
							'/api/globalsearch/billing-analysis?page=' +
							encodeURIComponent(currentPage) +
							'&size=' +
							encodeURIComponent(pageSize) +
							'&filters=' +
							encodeURIComponent(
								'isoCountryCode::eq::BR,transactionCode::in::' +
									transactionCodes +
									',billingAnalysisEndDate::gte::' +
									dataIni +
									',billingAnalysisEndDate::lte::' +
									dataFim
							); //console.log('Calling endpoint: ' + endpointCall);

						setTimeout(
							() => getPageBillingAnalysis(endpointCall),
							currentPage * delayPerRequest * pageSize
						);

						console.log('CurrentPage: ' + currentPage);
					}
				})

				.catch((error) => console.error('Erro:', error));
		}
	})

	.catch((error) => console.error('Erro:', error));

function getPageBillingAnalysis(endpointCall) {
	console.log('Calling endpoint: ' + endpointCall);

	fetch(endpointCall, {
		method: 'GET',

		credentials: 'include',
	})
		.then((response) => response.json())

		.then((data) => {
			fetch(destinationUrl + '/api/v1/bsp/capture', {
				method: 'POST',

				headers: {
					'Content-Type': 'application/json',

					Authorization: 'Bearer ' + token,
				},

				body: JSON.stringify(data),
			})
				.then((result) => {
					//confere se o envio funcionou

					if (result.status === 200) {
						console.log('Dados enviados com sucesso:');
					} else {
						result.json().then((data) => {
							console.error(
								'Erro ao enviar os dados. Código de status:' + result.status,
								data
							);
						});
					}
				})

				.catch((error) => console.error('Erro:', error));

			data.records.forEach((item, index) => {
				//console.log("Index: "+ index);

				//console.log('Delay: ' + index * delayPerRequest);

				let itemCopy = Object.assign({}, item);

				setTimeout(() => {
					getDocumentDetails(itemCopy); //console.log('Consulta Documento: ' + itemCopy.id);
				}, index * delayPerRequest);
			});
		}) //.then(result => console.log('Dados capturados com sucesso:', result))

		.catch((error) => console.error('Erro:', error));
}

async function getDocumentDetails(item) {
	if (item.id != null) {
		//captura documento detalhado

		endpointCall = bspUrl + '/api/document-enquiry/documents/' + item.id;

		console.log('Calling endpoint: ' + endpointCall);

		fetch(endpointCall, {
			method: 'GET',

			credentials: 'include',
		})
			.then((response) => response.json())

			.then((data) => {
				//enviar dados todos para gravação em nosso sistema

				return fetch(destinationUrl + '/api/v1/bsp/capture', {
					method: 'POST',

					headers: {
						'Content-Type': 'application/json',

						Authorization: 'Bearer ' + token,
					},

					body: JSON.stringify(data),
				})
					.then((result) => {
						//confere se o envio funcionou

						if (result.status === 200) {
							console.log('Dados enviados com sucesso:', item.id);
						} else {
							result.json().then((data) => {
								console.error(
									'Erro ao enviar os dados. Código de status:' + result.status,
									data
								);
							});
						}
					})

					.catch((error) => console.error('Erro:', error));
			}) //.then(response => console.log('Dados de detalhe capturados com sucesso:', response))

			.catch((error) => console.error('Erro:', error));
	} else {
		console.log('Documento não encontrado:', item);
	}
}
