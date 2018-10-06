import { overtimeConstants } from '../_constants/overtime.constants';

export function overtimeCategory(state = {}, action) {
  switch (action.type) {
    case overtimeConstants.GET_CATEGORY_REQUEST:
    	return {
        	loading: true
      	};
    case overtimeConstants.GET_OVERTIME_CATEGORY:
      	return {
          loading: false,
          projectListByEmp: action.overtimeCategory.projectListByEmployee,
          overtimeTypeList: action.overtimeCategory.overtimeTypeList,
          projectList: action.overtimeCategory.projectList,
          projectListByManager:action.overtimeCategory.projectListByManager

        };
    case overtimeConstants.GET_OVERTIME_CATEGORY_FAILURE:
      return {
        loading: false,
        error: action.error
      };
    default:
      return state
  }
}
