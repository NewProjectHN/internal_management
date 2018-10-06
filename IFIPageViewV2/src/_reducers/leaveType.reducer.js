import { leaveConstants } from '../_constants/leave.constants';

export function leaveCategory(state = {}, action) {
  switch (action.type) {
    case leaveConstants.GET_CATEGORY_REQUEST:
    	return {
        	loading: true
      	};
    case leaveConstants.GET_LEAVE_CATEGORY:
      	return {
          loading: false,
          projectListByEmp: action.leaveCategory.projectListByEmployee,
          leaveTypeList: action.leaveCategory.vacationTypeList,
          projectList: action.leaveCategory.projectList,
          projectListByManager: action.leaveCategory.projectListByManager
        };
    case leaveConstants.GET_LEAVE_CATEGORY_FAILURE:
      return {
        loading: false,
        error: action.error
      };
    default:
      return state
  }
}
