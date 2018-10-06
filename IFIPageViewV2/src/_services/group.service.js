import { authHeader } from '../_helpers/auth-header.js';
import { systemConstants } from '../_constants/system.constants.js';


export const GroupService = {
    getGroup
};


function getGroup(){
    const requestOptions = {
        method: "GET",
        headers: authHeader()
    };
    let url = systemConstants.API_URL + "/api/groups?page=1&pageSize=10";

    return fetch(url,requestOptions).then(handleResponse);
}


function handleResponse(response) {
    if (!response.ok) {
        return Promise.reject(response.statusText);
    }
    return response.json();
}
