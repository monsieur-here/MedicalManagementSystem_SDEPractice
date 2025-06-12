<script>
    import { goto } from "$app/navigation";
    import { page } from "$app/stores";

    $: user_type = $page.url.searchParams.get("user_type");
    let patientName = "";
    let admissionDate = "";
    let roomType = "";
    let roomNumber = "";
    let notes = "";
    let message = "";

    // Simulated available rooms/beds
    /** @type {{ [key: string]: string[] }} */
    const availableRooms = {
        General: ["G101", "G102", "G103"],
        "Semi-Private": ["SP201", "SP202"],
        Private: ["P301", "P302", "P303"],
    };

    // Dynamically update available room numbers
    // In plain JavaScript, no type assertion is needed here.
    // The runtime behavior remains the same.
    $: roomOptions = availableRooms[roomType]?.length
        ? availableRooms[roomType]
        : [];

    function handleRoomBooking() {
        if (!patientName || !admissionDate || !roomType || !roomNumber) {
            message = "⚠️ Please fill in all required fields.";
            return;
        }

        const now = new Date();
        const selectedDate = new Date(admissionDate);
        const tenDaysFromNow = new Date();
        tenDaysFromNow.setDate(now.getDate() + 10);
        const today = new Date(now.setHours(0, 0, 0, 0));

	// Check if the selected date is before today
	if (selectedDate < today) {
		message = "❌ Cannot select a past date.";
		return;
	}
    

        // Check if date is more than 10 days in future
        if (selectedDate > tenDaysFromNow) {
            message = "❌ You can only book rooms up to 10 days in advance.";
            return;
        }

        // If booking for today, ensure it's not before the current time
        const isToday =
            selectedDate.toDateString() === new Date().toDateString();
        if (isToday && now.getHours() >= 23) {
            message = "❌ Too late to book a room for today.";
            return;
        }

        const details = {
            patientName,
            admissionDate,
            roomType,
            notes,
        };

        alert("🎉 Room Booked successfully!");
        goto(`/homepage?user_type=Staff`);
    }
</script>

<div class="header">
    <a href={`/homepage?user_type=${user_type}`} class="home-button">Back</a>
    <h1>🏥 Room Booking</h1>
    <button class="logout-button" on:click={() => goto("/")}>Logout</button>
</div>
<div class="container">
    <form on:submit|preventDefault={handleRoomBooking}>
        <label>
            Patient Name:
            <input type="text" bind:value={patientName} required />
        </label>

        <label>
            Admission Date:
            <input
                type="date"
                bind:value={admissionDate}
                required
                min={new Date().toISOString().split("T")[0]}
                max={new Date(Date.now() + 10 * 24 * 60 * 60 * 1000)
                    .toISOString()
                    .split("T")[0]}
            />
        </label>

        <label>
            Room Type:
            <select bind:value={roomType} required>
                <option value="" disabled selected>Select Room Type</option>
                <option value="General">General</option>
                <option value="Semi-Private">Semi-Private</option>
                <option value="Private">Private</option>
            </select>
        </label>

        {#if roomOptions.length}
            <label>
                Available Room/Bed:
                <select bind:value={roomNumber} required>
                    <option value="" disabled selected>Select a room/bed</option
                    >
                    {#each roomOptions as room}
                        <option value={room}>{room}</option>
                    {/each}
                </select>
            </label>
        {/if}

        <label>
            Additional Notes:
            <textarea
                bind:value={notes}
                rows="3"
                placeholder="Optional notes..."
            ></textarea>
        </label>

        <button type="submit">Book Room</button>

        {#if message}
            <p class="message">{message}</p>
        {/if}
    </form>
</div>

<style>
    .container {
        max-width: 600px;
        margin: 2rem auto;
        padding: 2rem;
        background: #f9f9f9;
        border-radius: 10px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
        font-family: sans-serif;
    }

    h1 {
        text-align: center;
        color: #007bff;
        margin-bottom: 2rem;
    }

    form {
        display: flex;
        flex-direction: column;
        gap: 1rem;
    }

    label {
        display: flex;
        flex-direction: column;
        font-weight: bold;
        color: #333;
    }

    input,
    select,
    textarea {
        padding: 0.6rem;
        border: 1px solid #ccc;
        border-radius: 4px;
        margin-top: 0.3rem;
    }

    button {
        margin-top: 1rem;
        padding: 0.7rem;
        background-color: #007bff;
        color: white;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        font-weight: bold;
    }

    button:hover {
        background-color: #0056b3;
    }

    .message {
        color: red;
        font-weight: bold;
        margin-top: 1rem;
        text-align: center;
    }
    .header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 0rem 1rem;
        background-color: #f1f1f1;
        border-bottom: 1px solid #ddd;
    }
    .home-button {
        display: inline-block;
        margin: 1.5rem 2rem;
        padding: 0.6rem 1.2rem;
        background-color: #007bff;
        color: white;
        text-decoration: none;
        border-radius: 6px;
        font-weight: 500;
        transition: background-color 0.3s ease;
        box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
    }

    .home-button:hover {
        background-color: #0056b3;
    }
    .logout-button {
        background-color: #dc3545;
        color: white;
        border: none;
        margin: 1.5rem 2rem;
        border-radius: 6px;
        padding: 0.6rem 1.2rem;
        cursor: pointer;
        font-weight: 500;
        transition: background-color 0.3s ease;
        box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
    }

    .logout-button:hover {
        background-color: #c82333;
    }
</style>
