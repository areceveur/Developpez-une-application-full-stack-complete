import {Theme} from "../pages/themes/interfaces/theme.interface";

export interface User {
  id: number,
  username: string,
  email: string,
  created_at: Date,
  updated_at: Date
  subscriptions: Theme[];
}
