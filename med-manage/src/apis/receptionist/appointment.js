//

import { BASE_API_URL } from "$lib/config";

// RECEPTIONIST: GET: get appointment
export const getReceptionistAppointment = async () => {
  const myHeaders = new Headers();
  myHeaders.append("Cookie", "JSESSIONID=06E0804CBC82AF459E791FE8CAB0A83D");

  const requestOptions = {
    method: "GET",
    headers: myHeaders,
    redirect: "follow",
  };

  try {
    const response = await fetch(
      BASE_API_URL + "/receptionist/appointments",
      requestOptions
    );
    const result = await response.text();

    return JSON.parse(result)?.data?.appointments;
  } catch (error) {
    console.error(error);
    return error;
  }
};

// RECEPTIONIST: PUT: appointment
export const putReceptionistAppointment = async (payload) => {
  const myHeaders = new Headers();
  myHeaders.append("Content-Type", "application/json");
  myHeaders.append("Cookie", "JSESSIONID=06E0804CBC82AF459E791FE8CAB0A83D");

  const raw = JSON.stringify(payload);

  const requestOptions = {
    method: "PUT",
    headers: myHeaders,
    body: raw,
    redirect: "follow",
  };

  try {
    const response = await fetch(
      BASE_API_URL + "/receptionist/appointments",
      requestOptions
    );
    const result = await response.text();

    return JSON.parse(result);
  } catch (error) {
    console.error(error);
    return error;
  }
};
