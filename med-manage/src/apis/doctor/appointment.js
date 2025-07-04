import { BASE_API_URL } from "$lib/config";

// Doctor: GET: Appointments
export const getDoctorsAppointment = async (payload) => {
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
      BASE_API_URL + "/doctor/appointments?doctorId=" + userData?.id,
      requestOptions
    );
    const result = await response.text();
    return JSON.parse(result)?.data?.appointments;
  } catch (error) {
    console.error(error);
    return error;
  }
};

// Doctor: GET: get doctors
export const getDoctorsList = async () => {
  const myHeaders = new Headers();
  myHeaders.append("Cookie", "JSESSIONID=9D61B8DF14A896C123E4FEA0A8EA4F36");

  const requestOptions = {
    method: "GET",
    headers: myHeaders,
    redirect: "follow",
  };

  try {
    const response = await fetch(BASE_API_URL + "/doctors", requestOptions);
    const result = await response.text();

    return JSON.parse(result)?.data?.doctors;
  } catch (error) {
    console.error(error);
    return error;
  }
};
