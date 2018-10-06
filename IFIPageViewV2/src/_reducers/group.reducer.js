import {groupConstants} from '../_constants/group.constants';

export function group(state = {},action){
    switch(action.type){
        case groupConstants.GETALL_REQUEST_GROUP:
            return {
                loading: true
            };
        case groupConstants.GETALL_SUCCESS_GROUP:
            return{
                loading: false,
                groupList: action.groupBean.data
            }
        case groupConstants.GETALL_FAILURE:
            return{
                loading: false,
                error: action.error
            }

        default:
            return state;
    }

}