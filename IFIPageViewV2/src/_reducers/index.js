import { combineReducers } from 'redux';

import {overtimes} from './overtime.reducer';

import {leaves} from './leave.reducer';
import {popup} from './popup.reducer';
import {users} from './user.reducer';
import {authentication} from './authentication.reducer';
import {leaveCategory} from './leaveType.reducer';
import {alert} from './alert.reducer';
import {homes} from './home.reducer';
import {overtimeCategory} from './overtimeType.reducer';
import {project} from './project.reducer';
import {group} from './group.reducer';

import {employees} from './employee.reducer';

const rootReducer = combineReducers({
  popup,
  leaves,
  users,
  authentication,
  leaveCategory,
  alert,
  homes,
  overtimes,
  overtimeCategory,
  group,
  project,
  employees
});

export default rootReducer;
