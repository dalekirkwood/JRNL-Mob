package com.dale.jrnlmob.ui.settings

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Cloud
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.dale.jrnlmob.ui.theme.CatppuccinFrappe
import com.dale.jrnlmob.ui.theme.CatppuccinLatte
import com.dale.jrnlmob.ui.theme.CatppuccinMacchiato
import com.dale.jrnlmob.ui.theme.CatppuccinMocha
import com.dale.jrnlmob.ui.theme.DarkFlavor
import com.dale.jrnlmob.ui.theme.ThemeMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    state: SettingsState,
    onWebdavUrlChanged: (String) -> Unit,
    onUsernameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onJournalPathChanged: (String) -> Unit,
    onAutoSyncChanged: (Boolean) -> Unit,
    onTemperatureUnitChanged: (String) -> Unit,
    onThemeModeChanged: (ThemeMode) -> Unit,
    onDarkFlavorChanged: (DarkFlavor) -> Unit,
    onSave: () -> Unit,
    onTestConnection: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var passwordVisible by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Settings", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        onSave()
                        Toast.makeText(context, "Settings saved", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(
                            Icons.Rounded.Check,
                            contentDescription = "Save",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            ThemeSection(
                themeMode = state.themeMode,
                darkFlavor = state.darkFlavor,
                onThemeModeChanged = onThemeModeChanged,
                onDarkFlavorChanged = onDarkFlavorChanged
            )

            Spacer(modifier = Modifier.height(20.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            Spacer(modifier = Modifier.height(16.dp))

            WebdavSection(
                state = state,
                passwordVisible = passwordVisible,
                onPasswordVisibleChange = { passwordVisible = it },
                onWebdavUrlChanged = onWebdavUrlChanged,
                onUsernameChanged = onUsernameChanged,
                onPasswordChanged = onPasswordChanged,
                onJournalPathChanged = onJournalPathChanged,
                onTestConnection = onTestConnection
            )

            Spacer(modifier = Modifier.height(20.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            Spacer(modifier = Modifier.height(16.dp))

            SyncOptionsSection(
                state = state,
                onAutoSyncChanged = onAutoSyncChanged,
                onTemperatureUnitChanged = onTemperatureUnitChanged
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun ThemeSection(
    themeMode: ThemeMode,
    darkFlavor: DarkFlavor,
    onThemeModeChanged: (ThemeMode) -> Unit,
    onDarkFlavorChanged: (DarkFlavor) -> Unit
) {
    Text(
        text = "Appearance",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary
    )
    Spacer(modifier = Modifier.height(12.dp))

    Text(
        text = "Mode",
        style = MaterialTheme.typography.bodyMedium
    )
    Spacer(modifier = Modifier.height(8.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ThemeMode.entries.forEach { mode ->
            val selected = themeMode == mode
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .then(
                        if (selected) Modifier.border(
                            2.dp,
                            MaterialTheme.colorScheme.primary,
                            RoundedCornerShape(10.dp)
                        ) else Modifier
                    )
                    .clickable { onThemeModeChanged(mode) },
                shape = RoundedCornerShape(10.dp),
                color = if (selected) MaterialTheme.colorScheme.primaryContainer
                else MaterialTheme.colorScheme.surfaceVariant
            ) {
                Text(
                    text = when (mode) {
                        ThemeMode.LIGHT -> "Light"
                        ThemeMode.DARK -> "Dark"
                        ThemeMode.SYSTEM -> "System"
                    },
                    style = MaterialTheme.typography.labelLarge,
                    color = if (selected) MaterialTheme.colorScheme.onPrimaryContainer
                    else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 12.dp)
                )
            }
        }
    }

    if (themeMode != ThemeMode.LIGHT) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Dark theme",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DarkFlavor.entries.forEach { flavor ->
                val selected = darkFlavor == flavor
                val swatchColor = when (flavor) {
                    DarkFlavor.FRAPPE -> CatppuccinFrappe.Mauve
                    DarkFlavor.MACCHIATO -> CatppuccinMacchiato.Mauve
                    DarkFlavor.MOCHA -> CatppuccinMocha.Mauve
                }
                val bgColor = when (flavor) {
                    DarkFlavor.FRAPPE -> CatppuccinFrappe.Base
                    DarkFlavor.MACCHIATO -> CatppuccinMacchiato.Base
                    DarkFlavor.MOCHA -> CatppuccinMocha.Base
                }
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .then(
                            if (selected) Modifier.border(
                                2.dp,
                                MaterialTheme.colorScheme.primary,
                                RoundedCornerShape(10.dp)
                            ) else Modifier
                        )
                        .clickable { onDarkFlavorChanged(flavor) },
                    shape = RoundedCornerShape(10.dp),
                    color = bgColor
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 10.dp, horizontal = 12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .clip(CircleShape)
                        ) {
                            Surface(
                                modifier = Modifier.fillMaxSize(),
                                shape = CircleShape,
                                color = swatchColor
                            ) {}
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = when (flavor) {
                                DarkFlavor.FRAPPE -> "Frappe"
                                DarkFlavor.MACCHIATO -> "Macchiato"
                                DarkFlavor.MOCHA -> "Mocha"
                            },
                            style = MaterialTheme.typography.labelLarge,
                            color = when (flavor) {
                                DarkFlavor.FRAPPE -> CatppuccinFrappe.Text
                                DarkFlavor.MACCHIATO -> CatppuccinMacchiato.Text
                                DarkFlavor.MOCHA -> CatppuccinMocha.Text
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun WebdavSection(
    state: SettingsState,
    passwordVisible: Boolean,
    onPasswordVisibleChange: (Boolean) -> Unit,
    onWebdavUrlChanged: (String) -> Unit,
    onUsernameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onJournalPathChanged: (String) -> Unit,
    onTestConnection: () -> Unit
) {
    Text(
        text = "WebDAV Connection",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary
    )
    Spacer(modifier = Modifier.height(12.dp))

    OutlinedTextField(
        value = state.webdavUrl,
        onValueChange = onWebdavUrlChanged,
        label = { Text("Server URL") },
        placeholder = { Text("https://cloud.example.com/remote.php/dav/files/user") },
        supportingText = {
            Text(
                "OwnCloud: http://your-server/remote.php/dav/files/YOURUSER",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.outline
            )
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
        )
    )

    Spacer(modifier = Modifier.height(10.dp))

    OutlinedTextField(
        value = state.username,
        onValueChange = onUsernameChanged,
        label = { Text("Username") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
        )
    )

    Spacer(modifier = Modifier.height(10.dp))

    OutlinedTextField(
        value = state.password,
        onValueChange = onPasswordChanged,
        label = { Text("Password") },
        singleLine = true,
        visualTransformation = if (passwordVisible)
            androidx.compose.ui.text.input.VisualTransformation.None
        else
            PasswordVisualTransformation(),
        trailingIcon = {
            androidx.compose.material3.IconButton(onClick = { onPasswordVisibleChange(!passwordVisible) }) {
                Icon(
                    if (passwordVisible) Icons.Rounded.VisibilityOff else Icons.Rounded.Visibility,
                    contentDescription = if (passwordVisible) "Hide password" else "Show password"
                )
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Ascii),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
        )
    )

    Spacer(modifier = Modifier.height(10.dp))

    OutlinedTextField(
        value = state.journalPath,
        onValueChange = onJournalPathChanged,
        label = { Text("Journal file path") },
        placeholder = { Text("jrnl.txt") },
        supportingText = {
            Text(
                "Full path from your WebDAV root, e.g. 01. Documents/Vaults/jrnl.txt",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.outline
            )
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
        )
    )

    Spacer(modifier = Modifier.height(14.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        FilledTonalButton(
            onClick = onTestConnection,
            enabled = !state.isTesting && state.webdavUrl.isNotBlank(),
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(10.dp)
        ) {
            if (state.isTesting) {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Icon(Icons.Rounded.Cloud, contentDescription = null, modifier = Modifier.size(18.dp))
            }
            Spacer(modifier = Modifier.width(6.dp))
            Text("Test Connection")
        }
    }

    state.testResult?.let { result ->
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = result,
            style = MaterialTheme.typography.labelMedium,
            color = if (result == "Connected") MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.error
        )
    }
}

@Composable
private fun SyncOptionsSection(
    state: SettingsState,
    onAutoSyncChanged: (Boolean) -> Unit,
    onTemperatureUnitChanged: (String) -> Unit
) {
    Text(
        text = "Sync Options",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary
    )
    Spacer(modifier = Modifier.height(10.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Auto-sync",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Sync journal automatically in the background",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Switch(
            checked = state.autoSync,
            onCheckedChange = onAutoSyncChanged
        )
    }

    Spacer(modifier = Modifier.height(14.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text("Temperature unit", style = MaterialTheme.typography.bodyMedium)
            Text(
                if (state.temperatureUnit == "celsius") "Celsius (°C)" else "Fahrenheit (°F)",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Switch(
            checked = state.temperatureUnit == "fahrenheit",
            onCheckedChange = { fahrenheit ->
                onTemperatureUnitChanged(if (fahrenheit) "fahrenheit" else "celsius")
            }
        )
    }
}
