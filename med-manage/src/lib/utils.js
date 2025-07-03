import { goto } from "$app/navigation";
import { AUTH_ROUTES } from "./routes";

// function which will convert stream data to json format
export async function ConvertToJSONFromStream(response) {
  const responseText = await response.text();

  return JSON.parse(responseText);
}

//
export const handleLogOut = () => {
  localStorage.removeItem("usr_data");
  goto(AUTH_ROUTES.login.url);
};
