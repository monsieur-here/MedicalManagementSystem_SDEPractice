import { BASE_API_URL } from "$lib/config";

// Doctor: GET: Prescription
export const getPrescription = async (payload) => {
  const requestOptions = {
    method: "GET",
    redirect: "follow",
  };

  try {
    const response = await fetch(
      BASE_API_URL + "/patient/prescription?pageNo=1&pageSize=20",
      requestOptions
    );

    const result = await response.text();

    return JSON.parse(result);
  } catch (error) {
    console.error(error);
    return error;
  }
};
