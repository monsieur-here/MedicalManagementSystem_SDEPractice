import { BASE_API_URL } from "$lib/config";

// Patient: POST: Appointment
export const createAppointment = async (payload) => {
  const myHeaders = new Headers();
  myHeaders.append("Content-Type", "application/json");

  const raw = JSON.stringify(payload);

  const requestOptions = {
    method: "POST",
    headers: myHeaders,
    body: raw,
    redirect: "follow",
  };

  try {
    const response = await fetch(
      BASE_API_URL + "/patient/book-appointment",
      requestOptions
    );
    const result = await response.text();
    return JSON.parse(result);
  } catch (error) {
    console.error(error);
    return error;
  }
};
