import { authHeader } from '../_helpers/auth-header.js';
import { systemConstants } from '../_constants/system.constants.js';


export const homeService = {
    getCount,
    getOvertimeCount,
    getCountNeedApproveManager
};

function getCount(){
    const requestOptions = {
        method: "GET",
        headers: authHeader()
    };
    let url = systemConstants.API_URL + "/api/vacations/employee/count";
    return fetch(url,requestOptions).then(handleResponse);
}

    function getOvertimeCount(){
        const requestOptions = {
            method: "GET",
            headers: authHeader()
        };
        let url = systemConstants.API_URL + "/api/overtimes/employee/count";
        return fetch(url,requestOptions).then(handleResponse);
    }

    function getCountNeedApproveManager(){
        const requestOptions={
            method:"GET",
            headers:authHeader()
        };
        let url = systemConstants.API_URL+"/api/vacations/manager/count";
        return fetch(url,requestOptions).then(handleResponse);
    }
function handleResponse(response) {
    if (!response.ok) {
        return Promise.reject(response.statusText);
    }
    return response.json();
}
