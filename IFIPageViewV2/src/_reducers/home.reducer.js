import { homeConstants } from '../_constants/home.constants';

export function homes(state = {},action){
    switch(action.type){
        case homeConstants.GETALL_REQUEST:
            return {
                loading: true
            };
        case homeConstants.GETALL_SUCCESS:{
          
            return{
                loading: false,
                homeLeaveCount: action.homeLeaveCount
            }};
        case homeConstants.GETALL_FAILURE:
            return{
                loading: false,
                error: action.error
            };
        case homeConstants.GETALL_OVERTIME_REQUEST:
            return {
                loading: true
            };
        case homeConstants.GETALL_OVERTIME_SUCCESS:{
            
            return{
                loading: false,
                homeOvertimeCount: action.homeOvertimeCount
            }};
        case homeConstants.GETALL_OVERTIME_FAILURE:
            return{
                loading: false,
                error: action.error
            };
        case homeConstants.GETALL_MANAGER_REQUEST:
            return{
                loading:true
            };
        case homeConstants.GETALL_MANAGER_SUCCESS:
            return{
                loading:false,
                homeManagerCount:action.homeCountNeedApproveManager
            }
        case homeConstants.GETALL_MANAGER_FAILURE:
            return{
                loading:false,
                error:action.error
            }
        default:
            return state;
    }

}