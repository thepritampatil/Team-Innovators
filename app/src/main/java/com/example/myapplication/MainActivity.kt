package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.material.icons.filled.Language
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

// The Patient and ViewModel classes are assumed to be in their own files
// (e.g., Patient.kt, PatientViewModel.kt, AppViewModel.kt, etc.)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                AppNavigator()
            }
        }
    }
}

// region Navigation and Main Structure
@Composable
fun AppNavigator(appViewModel: AppViewModel = viewModel()) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            AshaHomePage(navController = navController)
        }
        composable("dashboard") {
            DashboardPage(navController = navController, appViewModel = appViewModel)
        }
        composable("patient_list") {
            PatientListPage(navController = navController, appViewModel = appViewModel)
        }
        composable("add_new_patient") {
            AddNewPatientPage(navController = navController, appViewModel = appViewModel)
        }
        composable("notifications") {
            NotificationPage(navController = navController, appViewModel = appViewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(title: String, navController: NavController, notificationViewModel: NotificationViewModel) {
    var showMenu by remember { mutableStateOf(false) }
    var showLanguageMenu by remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text(title, fontWeight = FontWeight.Bold) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        navigationIcon = {
            if (navController.previousBackStackEntry != null) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.onPrimaryContainer)
                }
            }
        },
        actions = {
            // Language Menu
            Box {
                IconButton(onClick = { showLanguageMenu = true }) {
                    Icon(Icons.Default.Language, contentDescription = "Language", tint = MaterialTheme.colorScheme.onPrimaryContainer)
                }
                DropdownMenu(expanded = showLanguageMenu, onDismissRequest = { showLanguageMenu = false }) {
                    DropdownMenuItem(text = { Text("English") }, onClick = { /* TODO */ showLanguageMenu = false })
                    DropdownMenuItem(text = { Text("हिंदी") }, onClick = { /* TODO */ showLanguageMenu = false })
                    DropdownMenuItem(text = { Text("मराठी") }, onClick = { /* TODO */ showLanguageMenu = false })
                }
            }

            // Notification Icon with Badge
            NotificationIcon(notificationViewModel = notificationViewModel) {
                navController.navigate("notifications")
            }

            // Other options Menu
            Box {
                IconButton(onClick = { showMenu = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Menu", tint = MaterialTheme.colorScheme.onPrimaryContainer)
                }
                DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                    DropdownMenuItem(text = { Text("Profile") }, onClick = { /* TODO */ })
                    DropdownMenuItem(text = { Text("Settings") }, onClick = { /* TODO */ })
                }
            }
        }
    )
}
// endregion

// region Asha Home Page and Dashboard
@Composable
fun AshaHomePage(navController: NavController, modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Welcome to",
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "ASHA SATHI",
                    fontWeight = FontWeight.Bold,
                    fontSize = 48.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Image(
                painter = painterResource(id = R.drawable.satymevjayte12),
                contentDescription = "Satyamev Jayate Logo",
                modifier = Modifier
                    .height(300.dp)
                    .width(300.dp)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(30.dp),
                modifier = Modifier.padding(bottom = 28.dp)
            ) {
                Text(
                    text = "Ministry of Science and Technology",
                    fontStyle = FontStyle.Italic,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
                )
                AnimatedEnterButton(navController = navController)
            }
        }
    }
}

@Composable
fun AnimatedEnterButton(navController: NavController) {
    val infiniteTransition = rememberInfiniteTransition(label = "button-scale")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800),
            repeatMode = RepeatMode.Reverse
        ), label = "scale-animation"
    )
    Button(
        onClick = { navController.navigate("dashboard") },
        modifier = Modifier
            .scale(scale)
            .width(280.dp)
            .height(56.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Text(
            text = "Go to Dashboard",
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardPage(navController: NavController, appViewModel: AppViewModel) {
    val dashboardItems = listOf(
        DashboardItem("Add New Patient", Icons.Default.PersonAdd, "add_new_patient"),
        DashboardItem("Patient Data", Icons.Default.People, "patient_list"),
        DashboardItem("Vaccination", Icons.Default.Vaccines, "vaccination"),
        DashboardItem("New Born Entries", Icons.Default.ChildFriendly, "new_born"),
        DashboardItem("Antenatal Mother", Icons.Default.PregnantWoman, "antenatal"),
        DashboardItem("Monthly Report", Icons.Default.Assessment, "report")
    )

    Scaffold(
        topBar = { AppTopBar(title = "Dashboard", navController = navController, notificationViewModel = appViewModel.notificationViewModel) }
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(dashboardItems) { item ->
                DashboardCard(item = item, onClick = {
                    if (item.route == "add_new_patient" || item.route == "patient_list") {
                        navController.navigate(item.route)
                    }
                    // TODO: Handle navigation for other items
                })
            }
        }
    }
}

data class DashboardItem(val title: String, val icon: ImageVector, val route: String)

@Composable
fun DashboardCard(item: DashboardItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.title,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = item.title,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
// endregion

// region Patient Management (List, Form, Card)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientListPage(navController: NavController, appViewModel: AppViewModel) {
    Scaffold(
        topBar = { AppTopBar(title = "Patient Data", navController = navController, notificationViewModel = appViewModel.notificationViewModel) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("add_new_patient") },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Patient", tint = MaterialTheme.colorScheme.onPrimary)
            }
        }
    ) { innerPadding ->
        val patients = appViewModel.patientViewModel.patients
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            if (patients.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No patients added yet.", color = MaterialTheme.colorScheme.onBackground)
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(patients, key = { it.id }) { patient ->
                        PatientCard(
                            patient = patient,
                            onEditClick = { /* TODO: Navigate to edit screen with patient id */ },
                            onDeleteClick = { /* TODO: Implement delete logic in ViewModel */ }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PatientCard(patient: Patient, onEditClick: () -> Unit, onDeleteClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            Text(patient.name, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Age: ${patient.age} | Gender: ${patient.gender}", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
            Text("Mobile: ${patient.mobile}", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = onEditClick) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                }
                IconButton(onClick = onDeleteClick) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewPatientPage(navController: NavController, appViewModel: AppViewModel) {
    // State for all form fields
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("Male") }
    var address by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var familyMembers by remember { mutableStateOf("") }
    var hasMedicalHistory by remember { mutableStateOf("No") }
    var medicalHistoryDetails by remember { mutableStateOf("") }
    var tookAnyVaccine by remember { mutableStateOf("No") }
    var bloodGroup by remember { mutableStateOf("A+") }
    var isMarried by remember { mutableStateOf("No") }
    var kidsCount by remember { mutableStateOf("") }
    var tookCovidVaccine by remember { mutableStateOf("No") }
    var tookPolioVaccine by remember { mutableStateOf("No") }

    // Form validation state
    val isFormValid by derivedStateOf {
        name.isNotBlank() && age.isNotBlank() && mobile.isNotBlank()
    }

    Scaffold(
        topBar = { AppTopBar(title = "Add New Patient Data", navController = navController, notificationViewModel = appViewModel.notificationViewModel) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 24.dp)
        ) {
            item { OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Patient Name*") }, modifier = Modifier.fillMaxWidth()) }
            item { OutlinedTextField(value = age, onValueChange = { age = it }, label = { Text("Age*") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth()) }
            item { FormDropdown(label = "Gender", options = listOf("Male", "Female", "Other"), selectedOption = gender, onOptionSelected = { gender = it }) }
            item { OutlinedTextField(value = address, onValueChange = { address = it }, label = { Text("Address") }, maxLines = 3, modifier = Modifier.fillMaxWidth()) }
            item { OutlinedTextField(value = mobile, onValueChange = { mobile = it }, label = { Text("Mobile Number*") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone), modifier = Modifier.fillMaxWidth()) }
            item { OutlinedTextField(value = familyMembers, onValueChange = { familyMembers = it }, label = { Text("Number of Family Members") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth()) }

            item { FormRadioButtons(label = "Any Medical History?", options = listOf("Yes", "No"), selectedOption = hasMedicalHistory, onOptionSelected = { hasMedicalHistory = it }) }
            if (hasMedicalHistory == "Yes") {
                item { OutlinedTextField(value = medicalHistoryDetails, onValueChange = { medicalHistoryDetails = it }, label = { Text("Describe Medical History") }, maxLines = 3, modifier = Modifier.fillMaxWidth()) }
            }

            item { FormRadioButtons(label = "Have you taken any vaccine?", options = listOf("Yes", "No"), selectedOption = tookAnyVaccine, onOptionSelected = { tookAnyVaccine = it }) }
            item { FormDropdown(label = "Blood Group", options = listOf("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"), selectedOption = bloodGroup, onOptionSelected = { bloodGroup = it }) }
            item { FormRadioButtons(label = "Married?", options = listOf("Yes", "No"), selectedOption = isMarried, onOptionSelected = { isMarried = it }) }
            if (isMarried == "Yes") {
                item { OutlinedTextField(value = kidsCount, onValueChange = { kidsCount = it }, label = { Text("Number of Kids") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth()) }
            }

            item { FormRadioButtons(label = "Have you taken Covid Vaccine?", options = listOf("Yes", "No"), selectedOption = tookCovidVaccine, onOptionSelected = { tookCovidVaccine = it }) }
            item { FormRadioButtons(label = "Have you taken Polio Vaccine?", options = listOf("Yes", "No"), selectedOption = tookPolioVaccine, onOptionSelected = { tookPolioVaccine = it }) }

            // Action Buttons
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedButton(
                        onClick = { navController.popBackStack() }, // Cancel
                        modifier = Modifier.weight(1f).height(48.dp)
                    ) {
                        Text("Cancel")
                    }
                    Button(
                        onClick = {
                            val newPatient = Patient(
                                name = name,
                                age = age,
                                gender = gender,
                                address = address,
                                mobile = mobile,
                                familyMembers = familyMembers,
                                medicalHistory = if (hasMedicalHistory == "Yes") medicalHistoryDetails else "No",
                                tookVaccine = tookAnyVaccine,
                                bloodGroup = bloodGroup,
                                isMarried = isMarried,
                                kidsCount = if (isMarried == "Yes") kidsCount else "0",
                                tookCovidVaccine = tookCovidVaccine,
                                tookPolioVaccine = tookPolioVaccine
                            )
                            appViewModel.patientViewModel.addPatient(newPatient)
                            appViewModel.notificationViewModel.addNotification("New patient '${newPatient.name}' added successfully")
                            navController.popBackStack() // Go back
                        },
                        modifier = Modifier.weight(1f).height(48.dp),
                        enabled = isFormValid // Submit
                    ) {
                        Text("Submit")
                    }
                }
            }
        }
    }
}
// endregion

// region Notification System
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationIcon(notificationViewModel: NotificationViewModel, onClick: () -> Unit) {
    val unreadCount by notificationViewModel.unreadCount

    IconButton(onClick = onClick) {
        BadgedBox(
            badge = {
                if (unreadCount > 0) {
                    Badge { Text("$unreadCount") }
                }
            }
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Notifications",
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationPage(navController: NavController, appViewModel: AppViewModel) {
    val notificationViewModel = appViewModel.notificationViewModel
    // When the user visits this page, clear the unread count
    LaunchedEffect(Unit) {
        notificationViewModel.clearUnreadCount()
    }

    Scaffold(topBar = { AppTopBar("Notifications", navController, notificationViewModel) }) { innerPadding ->
        val notifications = notificationViewModel.notifications
        if (notifications.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                Text("No notifications", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(innerPadding).background(MaterialTheme.colorScheme.background),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(notifications, key = { it.id }) { notification ->
                    NotificationItem(text = notification.message)
                }
            }
        }
    }
}

@Composable
fun NotificationItem(text: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(Modifier.padding(16.dp), Arrangement.spacedBy(16.dp), Alignment.CenterVertically) {
            Icon(Icons.Default.CircleNotifications, null, tint = MaterialTheme.colorScheme.primary)
            Text(text, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}
// endregion

// region Form Helpers
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormDropdown(label: String, options: List<String>, selectedOption: String, onOptionSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        OutlinedTextField(
            value = selectedOption, onValueChange = {}, readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor().fillMaxWidth()
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(text = { Text(option) }, onClick = { onOptionSelected(option); expanded = false })
            }
        }
    }
}

@Composable
fun FormRadioButtons(label: String, options: List<String>, selectedOption: String, onOptionSelected: (String) -> Unit) {
    Column {
        Text(label, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
            options.forEach { option ->
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { onOptionSelected(option) }) {
                    RadioButton(selected = (option == selectedOption), onClick = { onOptionSelected(option) })
                    Text(option, modifier = Modifier.padding(start = 2.dp))
                }
            }
        }
    }
}
// endregion
