import { BASE_API_URL } from "$lib/config";

// Patient: GET: Prescription
export const getPatientPrescription = async () => {
  const userData = JSON.parse(localStorage.getItem("user_data"));

  const myHeaders = new Headers();
  myHeaders.append("Cookie", "JSESSIONID=06E0804CBC82AF459E791FE8CAB0A83D");

  const requestOptions = {
    method: "GET",
    headers: myHeaders,
    redirect: "follow",
  };

  try {
    const response = await fetch(
      BASE_API_URL +
        "/patient/prescription?pageNo=1&pageSize=20?user_id=" +
        userData?.id,
      requestOptions
    );
    const result = await response.text();

    return JSON.parse(result);
  } catch (error) {
    console.error(error);
    return error;
  }
};
